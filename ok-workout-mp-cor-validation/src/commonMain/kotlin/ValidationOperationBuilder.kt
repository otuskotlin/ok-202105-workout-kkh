import ru.otus.otuskotlin.workout.validation.IValidator

class ValidationOperationBuilder<C, T> {

    fun validator(validator: IValidator<T>) {

    }

    fun on(block: C.() -> Unit) {

    }

    fun build() {

    }
}