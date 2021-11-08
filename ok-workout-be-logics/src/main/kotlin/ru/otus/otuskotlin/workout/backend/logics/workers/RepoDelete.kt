package ru.otus.otuskotlin.workout.backend.logics.workers

import handlers.CorChainDsl
import handlers.worker
import ru.otus.otuskotlin.workout.backend.common.context.BeContext
import ru.otus.otuskotlin.workout.backend.common.context.CorStatus
import ru.otus.otuskotlin.workout.backend.repo.common.exercise.DbExerciseIdRequest

internal fun CorChainDsl<BeContext>.repoDelete(title: String) = worker {
    this.title = title
    description = "The requested object will be deleted from the DB Repository"
    on { status == CorStatus.RUNNING }
    handle {
        val result = exerciseRepo.delete(DbExerciseIdRequest(requestExerciseId))
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