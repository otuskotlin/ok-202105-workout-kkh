package ru.otus.otuskotlin.workout.backend.logics.workers

import handlers.CorChainDsl
import handlers.worker
import ru.otus.otuskotlin.workout.backend.common.context.BeContext
import ru.otus.otuskotlin.workout.backend.common.context.CorStatus
import ru.otus.otuskotlin.workout.backend.repo.common.exercise.DbExerciseIdRequest

internal fun CorChainDsl<BeContext>.repoRead(title: String) = worker {
    this.title = title
    description = "Get data from DB"
    on {
        status == CorStatus.RUNNING
    }
    handle {
        val result = exerciseRepo.read(DbExerciseIdRequest(id = requestExerciseId))
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