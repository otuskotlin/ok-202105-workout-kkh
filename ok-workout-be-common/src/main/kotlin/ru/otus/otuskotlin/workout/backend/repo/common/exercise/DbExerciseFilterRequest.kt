package ru.otus.otuskotlin.workout.backend.repo.common.exercise

import ru.otus.otuskotlin.workout.backend.repo.common.IDbRequest

data class DbExerciseFilterRequest(
    val searchStr: String = "",
    val mode: SearchMode = SearchMode.TITLE
) : IDbRequest {
//    companion object {
//        val NONE = DbExerciseFilterRequest()
//    }

    enum class SearchMode {
//        NONE,
        TITLE,
        DESCRIPTION
    }
}