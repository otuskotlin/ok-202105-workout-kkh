package ru.workout.otuskotlin.workout.backend.common.repo.common.exercise

import ru.workout.otuskotlin.workout.backend.common.repo.common.IDbRequest

data class DbExerciseFilterRequest(
    val searchStr: String = ""
) : IDbRequest {
    companion object {
        val NONE = DbExerciseFilterRequest()
    }
}