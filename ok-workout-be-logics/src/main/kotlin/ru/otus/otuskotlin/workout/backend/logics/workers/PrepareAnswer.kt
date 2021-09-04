package ru.otus.otuskotlin.workout.backend.logics.workers

import handlers.CorChainDsl
import handlers.chain
import handlers.worker
import ru.workout.otuskotlin.workout.backend.common.context.BeContext

internal fun CorChainDsl<BeContext>.prepareAnswer(title: String) = chain {
    this.title = "Подготовка ответа"
    worker {
        this.title = "Успешный процесс"
        on {
            status in setOf(
                ru.workout.otuskotlin.workout.backend.common.context.CorStatus.RUNNING,
                ru.workout.otuskotlin.workout.backend.common.context.CorStatus.FINISHING
            )
        }
        handle {
            status = ru.workout.otuskotlin.workout.backend.common.context.CorStatus.SUCCESS
        }
    }

    worker {
        this.title = "Неуспешный процесс"
        on {
            status != ru.workout.otuskotlin.workout.backend.common.context.CorStatus.SUCCESS
        }
        handle {
            status = ru.workout.otuskotlin.workout.backend.common.context.CorStatus.ERROR
        }
    }
}