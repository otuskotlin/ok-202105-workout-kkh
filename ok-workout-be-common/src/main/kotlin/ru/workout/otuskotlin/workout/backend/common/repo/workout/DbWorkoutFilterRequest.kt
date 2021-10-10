package ru.workout.otuskotlin.workout.backend.common.repo.workout

import ru.workout.otuskotlin.workout.backend.common.repo.IDbRequest

class DbWorkoutFilterRequest(
    val searchStr: String = ""
) : IDbRequest {
    companion object {
        val NONE = DbWorkoutFilterRequest()
    }
}
