package handlers

import AbstractWorker
import CorComponentDsl
import ICorChainDsl
import ICorExec
import ICorExecDsl
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

fun <T> ICorChainDsl<T>.parallel(function: CorParallelDsl<T>.() -> Unit) {
    add(CorParallelDsl<T>().apply(function))
}

class CorParallelDsl<T>(
    private val workers: MutableList<ICorExecDsl<T>> = mutableListOf(),
    override var title: String = "",
    override var description: String = "",
) : CorComponentDsl<T>(), ICorChainDsl<T> {
    override fun add(worker: ICorExecDsl<T>) {
        workers.add(worker)
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
    override val blockOn: T.() -> Boolean,
    override val blockExcept: T.(e: Throwable) -> Unit
) : AbstractWorker<T>(blockOn, blockExcept) {
    override suspend fun handle(context: T): Unit = coroutineScope {
        execs
            .map { launch { it.exec(context) } }
            .toList()
            .forEach { it.join() }
    }
}