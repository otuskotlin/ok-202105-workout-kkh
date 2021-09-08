package ru.otus.otuskotlin.workout.validation

interface IValidationFieldError: IValidationError {
    val field: String
}
