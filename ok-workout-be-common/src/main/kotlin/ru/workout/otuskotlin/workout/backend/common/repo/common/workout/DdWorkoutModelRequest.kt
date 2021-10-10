package ru.workout.otuskotlin.workout.backend.common.repo.common.workout

import ru.workout.otuskotlin.workout.backend.common.models.WorkoutModel
import ru.workout.otuskotlin.workout.backend.common.repo.common.IDbRequest

class DdWorkoutModelRequest(
    val workout: WorkoutModel
) : IDbRequest
