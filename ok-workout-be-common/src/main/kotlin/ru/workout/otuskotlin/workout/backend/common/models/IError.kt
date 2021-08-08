package ru.workout.otuskotlin.workout.backend.common.models

interface IError {
    var field: String
    var level: Level
    var message: String

    enum class Level {
        ERROR,
        WARNING
    }
}