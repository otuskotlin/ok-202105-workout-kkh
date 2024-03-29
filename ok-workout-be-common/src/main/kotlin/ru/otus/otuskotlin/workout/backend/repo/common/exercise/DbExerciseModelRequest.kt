package ru.otus.otuskotlin.workout.backend.repo.common.exercise

import ru.otus.otuskotlin.workout.backend.common.models.ExerciseModel
import ru.otus.otuskotlin.workout.backend.repo.common.IDbRequest

class DbExerciseModelRequest(
    val exercise: ExerciseModel
) : IDbRequest