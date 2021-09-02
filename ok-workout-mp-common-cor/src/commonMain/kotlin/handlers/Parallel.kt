package handlers

import ICorChainDsl
import ICorExec
import ICorExecDsl
import ICorWorker
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

fun <T> ICorChainDsl<T>.parallel(function: CorParallelDsl<T>.() -> Unit) {
    add(CorParallelDsl<T>().apply(function))
}

class CorParallelDsl<T>(
    private val workers: MutableList<ICorExecDsl<T>> = mutableListOf(),
    override var title: String = "",
    override var description: String = "",
    private var blockOn: T.() -> Boolean = { true },
    private var blockHandle: T.() -> Unit = {},
    private var blockExcept: T.(e: Throwable) -> Unit = {}
) : ICorChainDsl<T> {
    override fun add(worker: ICorExecDsl<T>) {
        workers.add(worker)
    }

    override fun on(function: T.() -> Boolean) {
        blockOn = function
    }

    override fun handle(function: T.() -> Unit) {
        blockHandle = function
    }

    override fun except(function: T.(e: Throwable) -> Unit) {
        blockExcept = function
    }

    override fun build(): ICorExec<T> = CorParallel<T>(
        execs = workers.map { it.build() },
        title = title,
        description = description,
        blockOn = blockOn,
        blockExcept = blockExcept
    )
}

class CorParallel<T>(
    private val execs: List<ICorExec<T>>,
    override val title: String,
    override val description: String,
    val blockOn: T.() -> Boolean,
    val blockExcept: T.(e: Throwable) -> Unit
) : ICorWorker<T> {
    override suspend fun on(context: T): Boolean = blockOn(context)
    override suspend fun handle(context: T): Unit = coroutineScope {
        println(context)
        execs
            .map { launch { it.exec(context) } }
            .toList()
            .forEach { it.join() }
    }

    override suspend fun except(context: T, e: Throwable) = blockExcept(context, e)
}
