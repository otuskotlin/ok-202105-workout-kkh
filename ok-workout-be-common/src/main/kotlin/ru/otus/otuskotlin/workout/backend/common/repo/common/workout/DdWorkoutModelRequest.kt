package ru.otus.otuskotlin.workout.backend.common.repo.common.workout

import ru.otus.otuskotlin.workout.backend.common.models.WorkoutModel
import ru.otus.otuskotlin.workout.backend.common.repo.common.IDbRequest

class DdWorkoutModelRequest(
    val workout: WorkoutModel
) : IDbRequest
