package ru.workout.otuskotlin.workout.backend.common.repo.workout

import ru.workout.otuskotlin.workout.backend.common.models.WorkoutIdModel
import ru.workout.otuskotlin.workout.backend.common.repo.IDbRequest

class DdWorkoutIdRequest(
    val id: WorkoutIdModel
) : IDbRequest
