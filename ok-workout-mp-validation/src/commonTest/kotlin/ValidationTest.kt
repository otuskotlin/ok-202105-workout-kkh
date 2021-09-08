import ru.otus.otuskotlin.workout.validation.ValidationFieldError
import ru.otus.otuskotlin.workout.validation.validators.StringNonEmptyValidator
import kotlin.test.Test
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

class ValidationTest {
    private val emptyData = ""
    private val someData = "Some data"


    @Test
    fun stringTestValidationOk() {
        val validator = StringNonEmptyValidator()
        val result = validator validate someData

        assertTrue(result.isSuccess)
        assertTrue(result.errors.isEmpty())
    }

    @Test
    fun stringTestValidationError() {
        val validator = StringNonEmptyValidator()
        val result = validator validate emptyData

        println(result.errors.forEach {
            println("error: \"${it.message}\" because field: \"${(it as ValidationFieldError).field}\"")
        })

        assertTrue(result.errors.isNotEmpty())
        assertNotNull(result.errors.find { it.message.contains("empty") })
    }
}