package ru.otus.otuskotlin.workout.validation.cor

import ICorChainDsl
import handlers.worker
import ru.otus.otuskotlin.workout.validation.lib.ValidationResult
import ru.otus.otuskotlin.workout.validation.lib.Validator

fun <T> ICorChainDsl<T>.validation(block: ValidationBuilder.() -> Unit) {
    worker {
        title = "Валидация"
        description = "Валидация данных"
        handle {
            ValidationBuilder().apply(block).build().validate(this)
        }
    }
}

class ValidationBuilder<C>() {
    val errorHandler: C.(ValidationResult) -> Unit = {}
    val validators = mutableListOf<Validator<C>>()

    fun errorHandler() {

    }

    fun validate() {

    }
}