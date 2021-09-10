import kotlinx.coroutines.runBlocking
import org.junit.Test
import ru.otus.otuskotlin.workout.validation.IValidationError
import ru.otus.otuskotlin.workout.validation.ValidationResult
import ru.otus.otuskotlin.workout.validation.validators.StringNonEmptyValidator
import kotlin.test.assertEquals

class SimpleValidationTest {

    @Test
    fun pipelineValidation() {
        val chain = chain<TestContext> {
            validation {
                errorHandler { validationResult: ValidationResult ->
                    if (!validationResult.isSuccess) {
                        errors.addAll(validationResult.errors)
                    }
                }
                validate<String?> { validator(StringNonEmptyValidator()); on { x } }
                validate<String?> { validator(StringNonEmptyValidator()); on { y } }
            }
        }
        val c = TestContext()

        runBlocking {
            chain.build().exec(c)
            assertEquals(2, c.errors.size)
        }
    }

    data class TestContext(
        val x: String = "",
        val y: String = "",
        val errors: MutableList<IValidationError> = mutableListOf()
    )
}