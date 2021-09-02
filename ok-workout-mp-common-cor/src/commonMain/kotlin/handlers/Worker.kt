package handlers

import ICorChainDsl
import ICorExec
import ICorWorker
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
    private var blockOn: T.() -> Boolean = { true },
    private var blockHandle: T.() -> Unit = {},
    private var blockExcept: T.(e: Throwable) -> Unit = {}

) : ICorWorkerDsl<T> {
    override fun build(): ICorExec<T> = CorWorker<T>(
        title = title,
        description = description,
        blockOn = blockOn,
        blockHandle = blockHandle,
        blockExcept = blockExcept
    )

    override fun on(function: T.() -> Boolean) {
        blockOn = function
    }

    override fun handle(function: T.() -> Unit) {
        blockHandle = function
    }

    override fun except(function: T.(e: Throwable) -> Unit) {
        blockExcept = function
    }

}

class CorWorker<T>(
    override var title: String,
    override var description: String,
    val blockOn: T.() -> Boolean,
    val blockHandle: T.() -> Unit,
    val blockExcept: T.(e: Throwable) -> Unit
) : ICorWorker<T> {
    override suspend fun on(context: T): Boolean = blockOn(context)
    override suspend fun handle(context: T) = blockHandle(context)
    override suspend fun except(context: T, e: Throwable) = blockExcept(context, e)
}
