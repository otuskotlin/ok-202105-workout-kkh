package ru.otus.otuskotlin.workout.validation

class ValidationFieldError(
    override val message: String,
    override val field: String
) : IValidationError, IValidationFieldError