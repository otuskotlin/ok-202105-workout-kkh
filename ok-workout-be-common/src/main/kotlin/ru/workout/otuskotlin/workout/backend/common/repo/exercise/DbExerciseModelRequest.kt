package ru.workout.otuskotlin.workout.backend.common.repo.exercise

import ru.workout.otuskotlin.workout.backend.common.models.ExerciseModel
import ru.workout.otuskotlin.workout.backend.common.repo.IDbRequest

class DbExerciseModelRequest(
    val exercise: ExerciseModel
): IDbRequest