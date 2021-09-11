package ru.otus.otuskotlin.workout.validation.validators

import ru.otus.otuskotlin.workout.validation.IValidator
import ru.otus.otuskotlin.workout.validation.ValidationFieldError
import ru.otus.otuskotlin.workout.validation.ValidationResult

class ListNonEmptyValidator(
    private val field: String = "",
    private val message: String = "List must not be empty or null"
) : IValidator<List<*>?> {
    override fun validate(sample: List<*>?): ValidationResult {
        return if (sample.isNullOrEmpty()) {
            ValidationResult(
                listOf(
                    ValidationFieldError(
                        message = message,
                        field = field
                    )
                )
            )
        } else {
            ValidationResult.SUCCESS
        }
    }
}