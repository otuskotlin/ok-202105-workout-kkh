import kotlinx.coroutines.runBlocking
import org.junit.Test
import ru.otus.otuskotlin.workout.validation.cor.validation
import ru.otus.otuskotlin.workout.validation.lib.ValidationError
import ru.otus.otuskotlin.workout.validation.lib.ValidationResult
import ru.otus.otuskotlin.workout.validation.lib.validator.StringNotEmptyValidator
import kotlin.test.assertEquals

class ValidationCorTest {

    @Test
    fun `test validation in cor`() {

        val chain = chain<TestDataContext> {
            validation {
                errorHandler { result: ValidationResult ->
                    if (!result.isSuccess)
                        this.errors.addAll(result.errors)
                }
            }
            validate { validator(StringNotEmptyValidator()) on { x } }
            validate { validator(StringNotEmptyValidator()) on { y } }
        }


        runBlocking {
            val context = TestDataContext()

            assertEquals(context.errors.size, 2)
            chain.build().exec(context)
            assertEquals(context.errors.size, 2)
        }
    }

    data class TestDataContext(
        val x: String = "",
        val y: String = "",
        val errors: List<ValidationError> = emptyList()
    )
}