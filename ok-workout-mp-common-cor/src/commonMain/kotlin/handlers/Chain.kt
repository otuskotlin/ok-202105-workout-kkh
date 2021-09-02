package handlers

import ICorChainDsl
import ICorExec
import ICorExecDsl
import ICorWorker

fun <T> ICorChainDsl<T>.chain(function: CorChainDsl<T>.() -> Unit) {
    add(CorChainDsl<T>().apply(function))
}


class CorChainDsl<T>(
    private val workers: MutableList<ICorExecDsl<T>> = mutableListOf(),
    override val title: String = "",
    override val description: String = "",
    private var blockOn: T.() -> Boolean = { true },
    private var blockHandle: T.() -> Unit = {},
    private var blockExcept: T.(e: Throwable) -> Unit = {},

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

    override fun build(): ICorExec<T> = CorChain<T>(
        title = title,
        description = description,
        execs = workers.map { it.build() },
        blockOn = blockOn,
        blockExcept = blockExcept
    )
}

class CorChain<T>(
    private val execs: List<ICorExec<T>>,
    override val title: String,
    override val description: String,
    private val blockOn: T.() -> Boolean,
    private val blockExcept: T.(e: Throwable) -> Unit

) : ICorWorker<T> {
    override suspend fun on(context: T): Boolean = blockOn(context)

    override suspend fun handle(context: T) {
        execs.forEach { it.exec(context) }
    }

    override suspend fun except(context: T, e: Throwable) = blockExcept(context, e)
}

