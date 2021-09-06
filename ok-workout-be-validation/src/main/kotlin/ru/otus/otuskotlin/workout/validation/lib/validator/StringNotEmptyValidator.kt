package ru.otus.otuskotlin.workout.validation.lib.validator

import ru.otus.otuskotlin.workout.validation.lib.ValidationFieldError
import ru.otus.otuskotlin.workout.validation.lib.ValidationResult
import ru.otus.otuskotlin.workout.validation.lib.Validator

class StringNotEmptyValidator(
    private val field: String = "",
    private val message: String = "String is empty"
) : Validator<String> {
    override fun validate(data: String): ValidationResult {
        return if (data.isBlank()) {
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