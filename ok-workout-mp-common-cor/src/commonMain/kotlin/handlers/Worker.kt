package handlers

import AbstractWorker
import CorComponentDsl
import ICorChainDsl
import ICorExec
import ICorWorkerDsl

fun <T> ICorChainDsl<T>.worker(function: CorWorkerDsl<T>.() -> Unit) {
    add(CorWorkerDsl<T>().apply(function))
}

fun <T> ICorChainDsl<T>.worker(
    title: String,
    description: String = "",
    function: T.() -> Unit
) {
    add(
        CorWorkerDsl<T>(
            title = title,
            description = description,
            blockHandle = function
        )
    )
}

class CorWorkerDsl<T>(
    override var title: String = "",
    override var description: String = "",
    private var blockHandle: T.() -> Unit = {},
) : CorComponentDsl<T>(), ICorWorkerDsl<T> {
    override fun build(): ICorExec<T> = CorWorker<T>(
        title = title,
        description = description,
        blockOn = blockOn,
        blockHandle = blockHandle,
        blockExcept = blockExcept
    )

    override fun handle(function: T.() -> Unit) {
        blockHandle = function
    }
}

class CorWorker<T>(
    override val title: String,
    override val description: String,
    override val blockOn: T.() -> Boolean,
    private val blockHandle: T.() -> Unit,
    override val blockExcept: T.(e: Throwable) -> Unit
) : AbstractWorker<T>(blockOn, blockExcept) {
    override suspend fun handle(context: T) {
        println("$title")
        blockHandle(context)
    }
}