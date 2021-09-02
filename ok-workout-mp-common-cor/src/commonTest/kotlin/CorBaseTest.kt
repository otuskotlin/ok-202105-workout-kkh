import handlers.worker
import handlers.chain
import handlers.parallel

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
                    title = "other operation",
                    description = "another way of using worker"
                ) {
                    some++
                }
            }

            parallel {
                on { some < 15 }

                worker(title = "Increment some") {
                    handle { some += 10 }
                }
            }

//            printResult(title = "Печать результата")

        }.build()
    }
}

data class TestContext(
    var some: Int = 1
) {

}