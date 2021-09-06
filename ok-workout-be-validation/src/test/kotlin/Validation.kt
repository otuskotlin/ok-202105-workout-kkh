import org.junit.Test
import ru.otus.otuskotlin.workout.validation.lib.validator.StringNotEmptyValidator
import kotlin.test.assertFalse
import kotlin.test.assertNotNull

class Validation {
    @Test
    fun `string validation error`() {
        val data = ""

        val validator = StringNotEmptyValidator()
        val result = validator validate data

        assertFalse(result.isSuccess)
        assertNotNull(result.errors.find { it.message.contains("empty") })
    }

//    @Test
//    fun `string validation ok`() {
//        val data = ""
//
//        val validator = StringNotEmptyValidator()
//        val result = validator validate data
//
//        assertEquals(res, SUCCESS)
//        assertTrue(res.errors.isEmpty())
//    }
}