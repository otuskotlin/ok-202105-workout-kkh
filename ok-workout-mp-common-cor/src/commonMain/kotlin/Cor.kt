import handlers.CorChainDsl

fun <T> chain(function: CorChainDsl<T>.() -> Unit) = CorChainDsl<T>().apply(function)

interface ICorChainDsl<T> {
    fun add(worker: ICorExecDsl<T>)
}

interface ICorWorkerDsl<T> {
    fun handle(function: T.() -> Unit)
}

//interface ICorHandlerDsl<T> {
//    fun on(function: T.() -> Boolean)
//    fun except(function: T.(e: Throwable) -> Unit)
//}

interface ICorExecDsl<T> {
    val title: String
    val description: String
    fun build(): ICorExec<T>
}

interface ICorExec<T> {
    val title: String
    val description: String
    suspend fun exec(context: T)
}

interface ICorWorker<T> : ICorExec<T> {
    suspend fun on(context: T): Boolean
    suspend fun handle(context: T)
    suspend fun except(context: T, e: Throwable)

    override suspend fun exec(context: T) {
        try {
            if (on(context)) {
                handle(context)
            }
        } catch (e: Throwable) {
            except(context, e)
        }
    }
}

abstract class CorComponentDsl<T>(
    var blockOn: T.() -> Boolean = { true },
    var blockExcept: T.(e: Throwable) -> Unit = {}
) : ICorExecDsl<T> {

    fun on(function: T.() -> Boolean) {
        blockOn = function
    }

    fun except(function: T.(e: Throwable) -> Unit) {
        blockExcept = function
    }
}

abstract class AbstractWorker<T>(
    open val blockOn: T.() -> Boolean,
    open val blockExcept: T.(e: Throwable) -> Unit
) : ICorWorker<T> {
    override suspend fun on(context: T): Boolean = blockOn(context)
    override suspend fun except(context: T, e: Throwable) = blockExcept(context, e)
}