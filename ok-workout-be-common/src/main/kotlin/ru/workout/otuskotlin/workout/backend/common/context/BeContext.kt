package ru.workout.otuskotlin.workout.backend.common.context

import ru.workout.otuskotlin.workout.backend.common.models.ExerciseIdModel
import ru.workout.otuskotlin.workout.backend.common.models.ExerciseModel
import ru.workout.otuskotlin.workout.backend.common.models.IError

data class BeContext(
    var requestId: String = "",
    var requestExerciseId: ExerciseIdModel = ExerciseIdModel.NONE,
    var requestExercise: ExerciseModel = ExerciseModel(),
    var responseExercise: ExerciseModel = ExerciseModel(),
    var responseExercises: MutableList<ExerciseModel> = mutableListOf(),
    var errors: MutableList<IError> = mutableListOf()
)