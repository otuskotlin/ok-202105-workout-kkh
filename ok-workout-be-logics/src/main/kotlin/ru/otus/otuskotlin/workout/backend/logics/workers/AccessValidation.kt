package ru.otus.otuskotlin.workout.backend.logics.workers

import ICorChainDsl
import handlers.chain
import handlers.worker
import ru.otus.otuskotlin.workout.backend.common.context.BeContext
import ru.otus.otuskotlin.workout.backend.common.context.CorStatus
import ru.otus.otuskotlin.workout.backend.common.models.CommonErrorModel
import ru.otus.otuskotlin.workout.backend.logics.helpers.AccessTableConditions
import ru.otus.otuskotlin.workout.backend.logics.helpers.accessTable
import ru.otus.otuskotlin.workout.backend.logics.helpers.resolveRelationsTo

fun ICorChainDsl<BeContext>.accessValidation(title: String) = chain {
    this.title = title
    description = "Вычисление прав доступа по группе принципала и таблице прав доступа"
    on { status == CorStatus.RUNNING }
    worker("Вычисление отношения упражнения к принципалу") {
        dbExercise.principalRelations = dbExercise.resolveRelationsTo(principal)
    }
    worker("Вычисление доступа к упражнению") {
        permitted = dbExercise.principalRelations.flatMap { relation ->
            chainPermissions.map { permission ->
                AccessTableConditions(
                    operation = operation,
                    permission = permission,
                    relation = relation
                )
            }
        }
            .any {
                accessTable[it] ?: false
            }
    }
    worker {
        this.title = "Валидация прав доступа"
        description = "Проверка наличия прав для выполнения операции"
        on { !permitted }
        handle {
            addError(
                CommonErrorModel(message = "User is not allowed to this operation")
            )
        }
    }
}