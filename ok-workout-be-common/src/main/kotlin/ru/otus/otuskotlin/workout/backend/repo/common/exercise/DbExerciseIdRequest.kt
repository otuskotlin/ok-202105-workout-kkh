package ru.otus.otuskotlin.workout.backend.repo.common.exercise

import ru.otus.otuskotlin.workout.backend.common.models.ExerciseIdModel
import ru.otus.otuskotlin.workout.backend.repo.common.IDbRequest

data class DbExerciseIdRequest(
    val id: ExerciseIdModel
) : IDbRequest
