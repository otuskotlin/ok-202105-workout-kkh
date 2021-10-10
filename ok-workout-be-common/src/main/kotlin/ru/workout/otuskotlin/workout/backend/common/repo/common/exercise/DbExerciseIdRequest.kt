package ru.workout.otuskotlin.workout.backend.common.repo.common.exercise

import ru.workout.otuskotlin.workout.backend.common.models.ExerciseIdModel
import ru.workout.otuskotlin.workout.backend.common.repo.common.IDbRequest

data class DbExerciseIdRequest(
    val id: ExerciseIdModel
) : IDbRequest
