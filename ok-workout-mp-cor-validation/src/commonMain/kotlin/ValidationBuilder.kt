import ru.otus.otuskotlin.workout.validation.ValidationResult

class ValidationBuilder<C> {

    private var errorHandler: C.(ValidationResult) -> Unit = {}
    private var validators: MutableList<IValidationOperation<C, *>> = mutableListOf()

    fun errorHandler(block: C.(ValidationResult) -> Unit) {
        errorHandler = block
    }

    fun <T> validate(block: ValidationOperationBuilder<C, T>.() -> Unit) {
        val validator = ValidationOperationBuilder<C, T>(errorHandler).apply(block)
        validators.add(validator.build())
    }

    fun build() = PipelineValidation(validators)
}