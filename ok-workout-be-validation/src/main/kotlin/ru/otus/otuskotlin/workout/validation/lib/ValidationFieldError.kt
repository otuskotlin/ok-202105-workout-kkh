package ru.otus.otuskotlin.workout.validation.lib

class ValidationFieldError(override val message: String, val field: String) : ValidationError {
}