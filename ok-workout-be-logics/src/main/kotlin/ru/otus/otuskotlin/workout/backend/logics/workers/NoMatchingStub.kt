package ru.otus.otuskotlin.workout.backend.logics.workers

import handlers.CorChainDsl
import handlers.worker
import ru.otus.otuskotlin.workout.backend.common.context.BeContext
import ru.otus.otuskotlin.workout.backend.common.context.CorStatus

internal fun CorChainDsl<BeContext>.noMatchingStubs() = worker {
    this.title = "No matching stubCase"
    on {
        status == CorStatus.RUNNING
    }
    handle {
        status = CorStatus.FAILING
        addError(
            e = Exception("No matching worker for stubCase: $stubCase")
        )
    }
}