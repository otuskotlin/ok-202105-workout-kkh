package ru.otus.otuskotlin.workout.validation

interface IValidator<T> {
    infix fun validate(sample: T): ValidationResult
}