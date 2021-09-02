import kotlinx.coroutines.runBlocking
import kotlin.test.Test
import kotlin.test.assertEquals

class CorJvmTest {
    @Test
    fun testChain() {
        val chain = CorBaseTest.chain
        val context = TestContext()

        runBlocking {
            chain.exec(context)
        }
        assertEquals(22, context.some)
    }
}