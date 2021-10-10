package ru.workout.otuskotlin.workout.backend.common.repo.workout

import ru.workout.otuskotlin.workout.backend.common.models.WorkoutModel
import ru.workout.otuskotlin.workout.backend.common.repo.IDbRequest

class DdWorkoutModelRequest(
    val workout: WorkoutModel
) : IDbRequest
