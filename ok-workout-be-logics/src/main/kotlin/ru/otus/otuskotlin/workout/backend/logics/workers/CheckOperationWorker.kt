package ru.otus.otuskotlin.workout.backend.logics.workers

import handlers.CorChainDsl
import handlers.worker
import ru.workout.otuskotlin.workout.backend.common.context.BeContext
import ru.workout.otuskotlin.workout.backend.common.context.CorStatus

internal fun CorChainDsl<BeContext>.checkOperationWorker(title: String, targetOperation: BeContext.MpOperations) =
    worker {
        this.title = title
        this.description = "Проверка, что операция соответствует чейну"
        on {
            operation != targetOperation
        }
        handle {
            status = CorStatus.FAILING
            addError(
                e = Exception("Excepted $targetOperation but was $operation"),
            )
        }
    }