package ru.workout.otuskotlin.workout.backend.common.repo.common.workout

import ru.workout.otuskotlin.workout.backend.common.models.WorkoutIdModel
import ru.workout.otuskotlin.workout.backend.common.repo.common.IDbRequest

class DdWorkoutIdRequest(
    val id: WorkoutIdModel
) : IDbRequest
