import ru.otus.otuskotlin.workout.validation.ValidationResult

class ValidationBuilder<C> {
    fun errorHandler(function: C.(ValidationResult) -> Unit) {

    }
}