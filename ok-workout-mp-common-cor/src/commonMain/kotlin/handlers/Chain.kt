package handlers

import AbstractWorker
import CorComponentDsl
import ICorChainDsl
import ICorExec
import ICorExecDsl

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
    override val blockOn: T.() -> Boolean,
    override val blockExcept: T.(e: Throwable) -> Unit

) : AbstractWorker<T>(blockOn, blockExcept) {

    override suspend fun handle(context: T) {
        execs.forEach { it.exec(context) }
    }
}