//import handlers.worker
//import handlers.chain
//import kotlin.test.Test
//
//class CorBaseTest {
//
//    @Test
//    fun corTest() {
//        val chain = chain<TestContext> {
//            worker {
//                title = "Some operation"
//                description = "Description of an operation"
//
//                on { some > 0 }
//                handle { some += 10 }
//                except { e: Throwable -> println("Exception: ${e.message}") }
//            }
//
//            chain {
//                on { some > 0 }
//
//                worker(
//                    title = "other operation",
//                    description = "another way of using worker"
//                ) {
//                    some++
//                }
//            }
//
//            parallel {
//                on { some > 15 }
//
//                worker(title = "Increment some") {
//                    handle { some += 10 }
//                }
//            }
//
//            printResult(title = "Печать результата")
//
//        }.build()
//    }
//}
//
//data class TestContext(
//    var some: Int = 0
//) {
//
//}

//fun <T> chain(function: CorChainDsl<T>.() -> Unit): CorChainDsl<T> = CorChainDsl<T>().apply(function)
//fun <T> ICorChainDsl<T>.chain(function: CorChainDsl<T>.() -> Unit): CorChainDsl<T> = CorChainDsl<T>().apply(function)
//fun <T> ICorChainDsl<T>.parallel(function: CorParallelDsl<T>.() -> Unit): CorParallelDsl<T> =
//    CorParallelDsl<T>().apply(function)
//
//fun <T> ICorChainDsl<T>.worker(function: CorWorkerDsl<T>.() -> Unit) = CorWorkerDsl<T>().apply(function)
//fun <T> ICorChainDsl<T>.worker(title: String, description: String, function: T.() -> Unit) = CorWorkerDsl<T>(
//    title = title,
//    description = description,
//    blockHandle = function
//)
//
//fun ICorChainDsl<TestContext>.printResult(title: String, description: String = "") = worker(title, description) {
//    println("Some = $some")
//}
//
//class CorChainDsl<T>(
//    override var title: String = "",
//    override var description: String = "",
//    private val workers: MutableList<ICorExecDsl<T>> = mutableListOf(),
//    private var blockOn: T.() -> Boolean = { true },
//    private var blockExcept: T.(Throwable) -> Unit = {}
//) : ICorChainDsl<T> {
//
//    override fun on(function: T.() -> Boolean) {
//        blockOn = function
//    }
//
//    override fun except(function: T.(Throwable) -> Unit) {
//        blockExcept = function
//    }
//
//    override fun build() = CorChain<T>(
//        title = title,
//        description = description,
//        blockOn = blockOn,
//        blockExcept = blockExcept,
//        workers = workers.map { it.build() }
//    )
//
//    override fun add(worker: ICorExecDsl<T>) {
//        workers.add(worker)
//    }
//}
//
//class CorParallelDsl<T>(
//    override var title: String = "",
//    override var description: String = "",
//    private val workers: MutableList<ICorExecDsl<T>> = mutableListOf(),
//    private var blockOn: T.() -> Boolean = { true },
//    private var blockExcept: T.(Throwable) -> Unit = {}
//) : ICorChainDsl<T> {
//
//    override fun on(function: T.() -> Boolean) {
//        blockOn = function
//    }
//
//    override fun except(function: T.(Throwable) -> Unit) {
//        blockExcept = function
//    }
//
//    override fun build() = CorChain<T>(
//        title = title,
//        description = description,
//        blockOn = blockOn,
//        blockExcept = blockExcept,
//        workers = workers.map { it.build() }
//    )
//
//    override fun add(worker: ICorExecDsl<T>) {
//        workers.add(worker)
//    }
//}
//
//class CorWorkerDsl<T>(
//    override var title: String = "",
//    override var description: String = "",
//    var blockOn: T.() -> Boolean = { true },
//    var blockHandle: T.() -> Unit = { },
//    var blockExcept: T.(Throwable) -> Unit = { e: Throwable -> throw e }
//) : ICorWorkerDsl<T> {
//    override fun on(function: T.() -> Boolean) {
//        blockOn = function
//    }
//
//    override fun handle(function: T.() -> Unit) {
//        blockHandle = function
//    }
//
//    override fun except(function: T.(Throwable) -> Unit) {
//        blockExcept = function
//    }
//
//    override fun build(): ICorExec<T> = CorWorker<T>(
//        title = title,
//        description = description,
//        blockOn = blockOn,
//        blockHandle = blockHandle,
//        blockExcept = blockExcept
//    )
//}
//
//class CorWorker<T>(
//    val title: String,
//    val description: String = "",
//    val blockOn: T.() -> Boolean = { true },
//    val blockHandle: T.() -> Unit = { },
//    val blockExcept: T.(Throwable) -> Unit = { e: Throwable -> throw e }
//) : ICorWorker<T> {
//    override suspend fun on(ctx: T): Boolean = blockOn(ctx)
//    override suspend fun except(ctx: T, e: Throwable) = blockExcept(ctx, e)
//    override suspend fun handle(ctx: T) = blockHandle(ctx)
//}
//
//interface ICorChainDsl<T> : ICorExecDsl<T> {
//    fun on(function: T.() -> Boolean)
//    fun except(function: T.(Throwable) -> Unit)
//    fun add(worker: ICorExecDsl<T>)
//}
//
//interface ICorWorkerDsl<T> : ICorExecDsl<T> {
//    fun on(function: T.() -> Boolean)
//    fun handle(function: T.() -> Unit)
//    fun except(function: T.(Throwable) -> Unit)
//}
//
//interface ICorExecDsl<T> {
//    val title: String
//    val description: String
//    fun build(): ICorExec<T>
//}
//
//class CorParallel<T>(
//    val title: String,
//    val description: String = "",
//    val workers: List<ICorExec<T>>,
//    val blockOn: T.() -> Boolean = { true },
//    val blockExcept: T.(Throwable) -> Unit = { e: Throwable -> throw e }
//) : ICorWorker<T> {
//    override suspend fun on(ctx: T): Boolean = blockOn(ctx)
//    override suspend fun except(ctx: T, e: Throwable) = blockExcept(ctx, e)
//    override suspend fun handle(ctx: T) = coroutineScope {
//        workers
//            .map {
//                launch { it.exec(ctx) }
//            }
//            .toList()
//            .forEach {
//                it.join()
//            }
//    }
//}
//
//class CorChain<T>(
//    val title: String,
//    val description: String = "",
//    val workers: List<ICorExec<T>>,
//    val blockOn: T.() -> Boolean = { true },
//    val blockExcept: T.(Throwable) -> Unit = { e: Throwable -> throw e }
//) : ICorChain<T> {
//    override suspend fun on(ctx: T): Boolean = blockOn(ctx)
//    override suspend fun except(ctx: T, e: Throwable) = blockExcept(ctx, e)
//    override suspend fun handle(ctx: T) {
//        workers.forEach {
//            it.exec(ctx)
//        }
//    }
//}
//
//interface ICorChain<T> : ICorWorker<T> {
//}
//
//interface ICorExec<T> {
//    suspend fun exec(ctx: T)
//}
//
//interface ICorWorker<T> : ICorExec<T> {
//    override suspend fun exec(ctx: T) {
//        try {
//            if (on(ctx)) handle(ctx)
//        } catch (e: Throwable) {
//            except(ctx, e)
//        }
//    }
//
//    suspend fun on(ctx: T): Boolean
//    suspend fun handle(ctx: T)
//    suspend fun except(ctx: T, e: Throwable)
//}

