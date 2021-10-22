package ru.otus.otuskotlin.workout.backend.repo.common.workout

import ru.otus.otuskotlin.workout.backend.repo.common.IDbRequest

class DbWorkoutFilterRequest(
    val searchStr: String = ""
) : IDbRequest {
    companion object {
        val NONE = DbWorkoutFilterRequest()
    }
}
