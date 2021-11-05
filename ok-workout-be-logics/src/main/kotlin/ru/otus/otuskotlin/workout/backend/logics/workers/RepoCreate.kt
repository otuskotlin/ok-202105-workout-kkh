package ru.otus.otuskotlin.workout.backend.logics.workers

import handlers.CorChainDsl
import handlers.worker
import ru.otus.otuskotlin.workout.backend.common.context.BeContext
import ru.otus.otuskotlin.workout.backend.common.context.CorStatus
import ru.otus.otuskotlin.workout.backend.repo.common.exercise.DbExerciseModelRequest

internal fun CorChainDsl<BeContext>.repoCreate(title: String) = worker {
    this.title = title
    description = "Data from request are stored in the DB repository"
    on { status == CorStatus.RUNNING }
    handle {
        val result = exerciseRepo.create(DbExerciseModelRequest(exercise = dbExercise))
        val resultValue = result.result
        if (result.isSuccess && resultValue != null) {
            dbExercise = resultValue
        } else {
            result.errors.forEach {
                addError(it)
            }
        }
    }
}