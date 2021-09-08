package ru.otus.otuskotlin.workout.validation.lib.validator

import ru.otus.otuskotlin.workout.validation.lib.ValidationFieldError
import ru.otus.otuskotlin.workout.validation.lib.ValidationResult
import ru.otus.otuskotlin.workout.validation.lib.Validator

class NumberRangeValidator<T : Comparable<T>>(
    private val field: String,
    private val message: String = "Not in range",
    private val min: T,
    private val max: T
) : Validator<T> {
    override fun validate(data: T): ValidationResult {
        return if (data in min..max) {
            ValidationResult.SUCCESS
        } else {
            ValidationResult(
                listOf(
                    ValidationFieldError(
                        field = field,
                        message = message
                    )
                )
            )
        }
    }
}