package ru.otus.otuskotlin.workout.backend.common.repo.common.exercise

import ru.otus.otuskotlin.workout.backend.common.repo.common.IDbRequest

data class DbExerciseFilterRequest(
    val searchStr: String = "",
    val mode: SearchMode = SearchMode.NONE
) : IDbRequest {
    companion object {
        val NONE = DbExerciseFilterRequest()
    }

    enum class SearchMode {
        NONE,
        TITLE,
        DESCRIPTION
    }
}