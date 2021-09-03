import handlers.worker
import handlers.chain
import handlers.parallel
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

class CorBaseTest {

    companion object {
        val chain = chain<TestContext> {
            worker {
                title = "Some operation"
                description = "Description of an operation"

                on { some > 0 }
                handle { some += 10 }
                except { e: Throwable -> println("Exception: ${e.message}") }
            }

            chain {
                on { some > 0 }

                worker(
                    title = "other operation: one",
                    description = "another way of using worker"
                ) {
                    some++
                }

                worker(
                    title = "other operation: two",
                    description = "another way of using worker"
                ) {
                    some--
                }

                worker(
                    title = "other operation: three",
                    description = "another way of using worker"
                ) {
                    some++
                }

            }

            parallel {
                on { some < 15 }

                worker(title = "one") {
                    for (i in 0..100000) {
                        some++
                    }
                }

                worker(title = "two") {
                    some -= 10
                }

                worker(title = "three") {
                    some += 10
                }
            }

            printResult()

        }.build()
    }
}

private fun ICorChainDsl<TestContext>.printResult() = worker(title = "Print example") {
    println("some = $some")
}

data class TestContext(
    var some: Int = 1
) {

}