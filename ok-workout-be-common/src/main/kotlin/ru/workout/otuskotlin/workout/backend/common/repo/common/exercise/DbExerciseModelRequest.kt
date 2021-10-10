package ru.workout.otuskotlin.workout.backend.common.repo.common.exercise

import ru.workout.otuskotlin.workout.backend.common.models.ExerciseModel
import ru.workout.otuskotlin.workout.backend.common.repo.common.IDbRequest

class DbExerciseModelRequest(
    val exercise: ExerciseModel
) : IDbRequest