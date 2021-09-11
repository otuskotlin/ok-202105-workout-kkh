package ru.otus.otuskotlin.workout.backend.logics.helpers

import ValidationBuilder
import handlers.CorChainDsl
import ru.otus.otuskotlin.workout.validation.IValidationFieldError
import ru.workout.otuskotlin.workout.backend.common.context.BeContext
import ru.workout.otuskotlin.workout.backend.common.context.CorStatus
import ru.workout.otuskotlin.workout.backend.common.models.CommonErrorModel
import workers.validation

fun CorChainDsl<BeContext>.validationLogics(block: ValidationBuilder<BeContext>.() -> Unit) = validation {
    errorHandler { validationResult ->
        if (validationResult.isSuccess) return@errorHandler
        val errors = validationResult.errors.map {

            CommonErrorModel(
                message = it.message,
                field = if (it is IValidationFieldError) it.field else ""
            )
        }
        this.errors.addAll(errors)
        status = CorStatus.FAILING
    }
    block()
}