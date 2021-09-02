import kotlinx.coroutines.runBlocking
import kotlin.test.Test

class CorJvmTest {
    @Test
    fun testChain() {
        val chain = CorBaseTest.chain
        val context = TestContext()

        runBlocking {
            chain.exec(context)
        }
    }
}