package ru.workout.otuskotlin.workout.backend.common.repo.exercise

import ru.workout.otuskotlin.workout.backend.common.repo.IDbRequest

data class DbExerciseFilterRequest(
    val searchStr: String = ""
) : IDbRequest {
    companion object {
        val NONE = DbExerciseFilterRequest()
    }
}