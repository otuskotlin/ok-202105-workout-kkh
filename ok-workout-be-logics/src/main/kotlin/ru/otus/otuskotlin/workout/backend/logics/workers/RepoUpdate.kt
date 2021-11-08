package ru.otus.otuskotlin.workout.backend.logics.workers

import handlers.CorChainDsl
import handlers.worker
import ru.otus.otuskotlin.workout.backend.common.context.BeContext
import ru.otus.otuskotlin.workout.backend.common.context.CorStatus
import ru.otus.otuskotlin.workout.backend.repo.common.exercise.DbExerciseModelRequest

internal fun CorChainDsl<BeContext>.repoUpdate(title: String) = worker {
    this.title = title
    description = "Data from request updates the DB Repository object"
    on { status == CorStatus.RUNNING }
    handle {
        val result = exerciseRepo.update(DbExerciseModelRequest(requestExercise))
        val resultValue = result.result
        if (resultValue != null && result.isSuccess) {
            dbExercise = resultValue
        } else {
            result.errors.forEach {
                addError(it)
            }
        }
    }
}