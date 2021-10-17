package ru.otus.otuskotlin.workout.backend.logics.workers

import handlers.CorChainDsl
import handlers.chain
import handlers.worker
import ru.otus.otuskotlin.workout.backend.common.context.BeContext
import ru.otus.otuskotlin.workout.backend.common.context.CorStatus

internal fun CorChainDsl<BeContext>.prepareAnswer(title: String) = chain {
    this.title = "Подготовка ответа"
    worker {
        this.title = "Успешный процесс"
        on {
            status in setOf(
                CorStatus.RUNNING,
                CorStatus.FINISHING
            )
        }
        handle {
            status = CorStatus.SUCCESS
        }
    }

    worker {
        this.title = "Неуспешный процесс"
        on {
            status != CorStatus.SUCCESS
        }
        handle {
            status = CorStatus.ERROR
        }
    }
}