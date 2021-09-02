package handlers

import CorComponentDsl
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
) : CorComponentDsl<T>(), ICorChainDsl<T> {
    override fun add(worker: ICorExecDsl<T>) {
        workers.add(worker)
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