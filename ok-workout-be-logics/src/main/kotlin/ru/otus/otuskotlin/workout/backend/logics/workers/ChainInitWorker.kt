package ru.otus.otuskotlin.workout.backend.logics.workers

import handlers.CorChainDsl
import handlers.worker
import ru.workout.otuskotlin.workout.backend.common.context.BeContext
import ru.workout.otuskotlin.workout.backend.common.context.CorStatus

internal fun CorChainDsl<BeContext>.chainInitWorker(title: String) = worker {
    this.title = title
    on {
        status == CorStatus.NONE
    }
    handle {
        status = CorStatus.RUNNING
    }
}