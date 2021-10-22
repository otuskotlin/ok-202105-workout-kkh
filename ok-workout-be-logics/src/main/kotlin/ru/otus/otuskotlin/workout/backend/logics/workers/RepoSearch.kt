package ru.otus.otuskotlin.workout.backend.logics.workers

import handlers.CorChainDsl
import handlers.worker
import ru.otus.otuskotlin.workout.backend.common.context.BeContext
import ru.otus.otuskotlin.workout.backend.common.repo.common.exercise.DbExerciseFilterRequest

internal fun CorChainDsl<BeContext>.repoSearch(title: String) = worker {
    this.title = title
    description = "Search for exercises"
    handle {
        val result = exerciseRepo.search(DbExerciseFilterRequest(requestSearchExercise))
        if (result.isSuccess) {
            foundExercises = result.result.toMutableList()
        } else {
            result.errors.forEach {
                addError(it)
            }
        }
    }
}