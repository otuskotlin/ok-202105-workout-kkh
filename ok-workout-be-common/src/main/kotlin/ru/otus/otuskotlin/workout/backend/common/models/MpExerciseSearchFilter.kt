package ru.otus.otuskotlin.workout.backend.common.models

import ru.otus.otuskotlin.workout.backend.repo.common.exercise.DbExerciseFilterRequest

data class MpExerciseSearchFilter(
    var searchStr: String = "",
    var authorId: AuthorIdModel = AuthorIdModel.NONE,
    var searchTypes: MutableSet<MpSearchTypes> = mutableSetOf(),
    var mode: DbExerciseFilterRequest.SearchMode = DbExerciseFilterRequest.SearchMode.TITLE
)