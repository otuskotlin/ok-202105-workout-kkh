package ru.otus.otuskotlin.workout.backend.repo.common.exercise

import ru.otus.otuskotlin.workout.backend.common.models.AuthorIdModel
import ru.otus.otuskotlin.workout.backend.common.models.MpExerciseSearchFilter
import ru.otus.otuskotlin.workout.backend.common.models.MpSearchTypes
import ru.otus.otuskotlin.workout.backend.repo.common.IDbRequest

data class DbExerciseFilterRequest(
    val searchStr: String = "",
    val authorId: AuthorIdModel = AuthorIdModel.NONE,
    val mode: SearchMode = SearchMode.TITLE,
    val searchTypes: Set<MpSearchTypes> = setOf()
) : IDbRequest {
    companion object {
        val NONE = DbExerciseFilterRequest()
        fun of(sf: MpExerciseSearchFilter) = DbExerciseFilterRequest(
            searchStr = sf.searchStr,
            authorId = sf.authorId,
            mode = sf.mode,
            searchTypes = sf.searchTypes
        )
    }

    enum class SearchMode {
//        NONE,
        TITLE,
        DESCRIPTION
    }
}