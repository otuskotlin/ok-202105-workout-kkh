package ru.workout.otuskotlin.workout.backend.common.repo.common.workout

import ru.workout.otuskotlin.workout.backend.common.repo.common.IDbRequest

class DbWorkoutFilterRequest(
    val searchStr: String = ""
) : IDbRequest {
    companion object {
        val NONE = DbWorkoutFilterRequest()
    }
}