import ru.otus.otuskotlin.workout.validation.IValidator
import ru.otus.otuskotlin.workout.validation.ValidationResult

class DefaultValidationOperation<C, T>(
    private val value: C.() -> T,
    private val validator: IValidator<T>,
    private val errorHandler: C.(ValidationResult) -> Unit
) : IValidationOperation<C, T> {
    override fun exec(context: C) {
        val value = context.value()
        val validationResult = validator.validate(value)
        context.errorHandler(validationResult)
    }
}