package ru.otus.otuskotlin.workout.validation.lib

interface Validator<T> {
    infix fun validate(data: T): ValidationResult
}