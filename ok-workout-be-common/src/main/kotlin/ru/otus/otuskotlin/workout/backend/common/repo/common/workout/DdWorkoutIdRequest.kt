package ru.otus.otuskotlin.workout.backend.common.repo.common.workout

import ru.otus.otuskotlin.workout.backend.common.models.WorkoutIdModel
import ru.otus.otuskotlin.workout.backend.common.repo.common.IDbRequest

class DdWorkoutIdRequest(
    val id: WorkoutIdModel
) : IDbRequest
