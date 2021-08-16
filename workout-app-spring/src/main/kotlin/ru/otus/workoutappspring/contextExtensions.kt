package ru.otus.workoutappspring

import ru.workout.otuskotlin.workout.backend.common.context.BeContext
import ru.workout.otuskotlin.workout.backend.common.models.CommonErrorModel
import ru.workout.otuskotlin.workout.backend.common.models.IError

fun BeContext.addError(lambda: CommonErrorModel.() -> Unit) =
    apply {
        errors.add(
            CommonErrorModel(
                field = "-",
                level = IError.Level.ERROR,
            ).apply(lambda)
        )
    }