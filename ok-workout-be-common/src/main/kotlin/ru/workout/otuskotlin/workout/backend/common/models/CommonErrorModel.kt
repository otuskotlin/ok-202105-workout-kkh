package ru.workout.otuskotlin.workout.backend.common.models

class CommonErrorModel(
    override var field: String = "",
    override var level: IError.Level = IError.Level.ERROR,
    override var message: String = ""
) : IError