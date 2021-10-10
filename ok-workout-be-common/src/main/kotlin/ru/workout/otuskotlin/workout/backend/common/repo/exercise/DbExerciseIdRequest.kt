package ru.workout.otuskotlin.workout.backend.common.repo.exercise

import ru.workout.otuskotlin.workout.backend.common.models.ExerciseIdModel
import ru.workout.otuskotlin.workout.backend.common.repo.IDbRequest

data class DbExerciseIdRequest(
    val id: ExerciseIdModel
) : IDbRequest
