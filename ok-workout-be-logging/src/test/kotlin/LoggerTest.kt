import kotlinx.coroutines.runBlocking
import ru.otus.otuskotlin.workout.logging.mpLogger
import java.io.ByteArrayOutputStream
import java.io.PrintStream
import kotlin.test.Test
import kotlin.test.assertTrue

class LoggerTest {
    private val logId = "test-logger"

    @Test
    fun loggerInit() {
        val output = invokeLogger {
            println("Some action")
        }

        assertTrue(Regex(".*Entering $logId.*").containsMatchIn(output.toString()))
        assertTrue(output.toString().contains("Some action"))
        assertTrue(Regex(".*Finishing $logId.*").containsMatchIn(output.toString()))

    }

    @Test
    fun loggerFails() {
        val output = invokeLogger {
            throw RuntimeException("Some action")
        }

        assertTrue(Regex(".*Entering $logId.*").containsMatchIn(output.toString()))
        assertTrue(Regex(".*Failing $logId.*").containsMatchIn(output.toString()))
    }

    private fun invokeLogger(block: suspend () -> Unit): ByteArrayOutputStream {
        val outputStreamCaptor = outputStreamCaptor()

        try {
            runBlocking {
                val logger = mpLogger(this::class.java)
                logger.doWithLogging(logId, block = block)
            }
        } catch (ignore: RuntimeException) {
        }
        return outputStreamCaptor
    }

    private fun outputStreamCaptor(): ByteArrayOutputStream {

        return ByteArrayOutputStream().apply {
            System.setOut(PrintStream(this))
        }
    }
}