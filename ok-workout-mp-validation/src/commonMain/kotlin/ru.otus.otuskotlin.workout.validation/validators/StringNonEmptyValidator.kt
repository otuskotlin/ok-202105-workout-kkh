package ru.otus.otuskotlin.workout.validation.validators

import ru.otus.otuskotlin.workout.validation.IValidator
import ru.otus.otuskotlin.workout.validation.ValidationFieldError
import ru.otus.otuskotlin.workout.validation.ValidationResult

class StringNonEmptyValidator(
    private val field: String = "",
    private val message: String = "String must not be empty or null"
) : IValidator<String?> {
    override fun validate(sample: String?): ValidationResult {
        println("Start validate. Sample is $sample")
        return if (sample.isNullOrBlank()) {
            println("error")
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
