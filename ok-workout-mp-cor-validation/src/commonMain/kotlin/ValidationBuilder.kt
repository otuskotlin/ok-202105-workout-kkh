import ru.otus.otuskotlin.workout.validation.ValidationResult

class ValidationBuilder<C> {

    fun errorHandler(block: C.(ValidationResult) -> Unit) {

    }

    fun <T> validate(block: ValidationOperationBuilder<C, T>.() -> Unit) {

    }

    fun build() {

    }
}