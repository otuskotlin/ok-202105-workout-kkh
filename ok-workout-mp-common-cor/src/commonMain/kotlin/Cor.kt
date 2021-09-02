import handlers.CorChainDsl

fun <T> chain(function: CorChainDsl<T>.() -> Unit) = CorChainDsl<T>().apply(function)

interface ICorChainDsl<T> : ICorExecDsl<T>, ICorHandlerDsl<T> {
    fun add(worker: ICorExecDsl<T>)
}

interface ICorWorkerDsl<T> : ICorExecDsl<T>, ICorHandlerDsl<T> {

}

interface ICorHandlerDsl<T> {
    fun on(function: T.() -> Boolean)
    fun handle(function: T.() -> Unit)
    fun except(function: T.(e: Throwable) -> Unit)
}

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
