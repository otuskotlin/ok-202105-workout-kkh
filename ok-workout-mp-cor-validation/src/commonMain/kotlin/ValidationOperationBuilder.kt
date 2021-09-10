import ru.otus.otuskotlin.workout.validation.IValidator
import ru.otus.otuskotlin.workout.validation.ValidationResult
import ru.otus.otuskotlin.workout.validation.validators.StringNonEmptyValidator

class ValidationOperationBuilder<C, T>(
    private var errorHandler: C.(ValidationResult) -> Unit = {}
) {

    private lateinit var validator: IValidator<T>
    private lateinit var value: C.() -> T

    fun validator(validator: IValidator<T>) {
        this.validator = validator
    }

    fun on(block: C.() -> T) {
        value = block
    }

    fun build(): IValidationOperation<C, T> {
        return DefaultValidationOperation(
            value = value,
            validator = validator,
            errorHandler = errorHandler
        )
    }
}