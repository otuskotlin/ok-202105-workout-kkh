import ru.otus.otuskotlin.workout.validation.IValidator
import workers.DefaultValidationOperation

class ValidationOperationBuilder<C, T> {

    fun validator(validator: IValidator<T>) {

    }

    fun on(block: C.() -> Unit) {

    }

    fun build() = DefaultValidationOperation<C, T>(

    )
}