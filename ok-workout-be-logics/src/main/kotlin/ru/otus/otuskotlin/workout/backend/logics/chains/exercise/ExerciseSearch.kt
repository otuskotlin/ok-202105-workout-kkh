package ru.otus.otuskotlin.workout.backend.logics.chains.exercise

import ICorExec
import chain
import handlers.chain
import handlers.worker
import ru.otus.otuskotlin.workout.backend.logics.chains.stubs.excercise.exerciseSearchStub
import ru.otus.otuskotlin.workout.backend.logics.helpers.validationLogics
import ru.otus.otuskotlin.workout.validation.validators.StringNonEmptyValidator
import ru.otus.otuskotlin.workout.backend.common.context.BeContext
import ru.otus.otuskotlin.workout.backend.common.context.CorStatus
import ru.otus.otuskotlin.workout.backend.common.models.MpSearchTypes
import ru.otus.otuskotlin.workout.backend.common.models.MpUserPermissions
import ru.otus.otuskotlin.workout.backend.logics.workers.*
import ru.otus.otuskotlin.workout.backend.logics.workers.chainInitWorker
import ru.otus.otuskotlin.workout.backend.logics.workers.checkOperationWorker
import ru.otus.otuskotlin.workout.backend.logics.workers.chooseDb
import ru.otus.otuskotlin.workout.backend.logics.workers.prepareAnswer

object ExerciseSearch : ICorExec<BeContext> by chain<BeContext>({
    checkOperationWorker(
        title = "Проверка, что операция соответствует чейну",
        targetOperation = BeContext.MpOperations.SEARCH
    )

    chainInitWorker("Инициализация чейна")

    chooseDb(title = "Выбираем БД или STUB")

    exerciseSearchStub(title = "Обработка стабкейса для SEARCH")

    // validation
//    validationLogics {
//        validate<String?> {
//            on { requestSearchExercise }
//            validator(StringNonEmptyValidator(field = "requestSearchExercise"))
//        }
//    }

    chainPermissions("Вычисление разрешений для пользователя")

    chain {
        title = "Подготовка поискового запроса"
        description = "Добавление ограничений в поисковый запрос согласно правам доступа"
        on {
            status == CorStatus.RUNNING
        }
        worker {
            title = "Определение типа поиска"
            description = title
            handle {
                dbExerciseFilter.searchTypes = listOf(
                    MpSearchTypes.AUTHOR.takeIf { chainPermissions.contains(MpUserPermissions.SEARCH_AUTHOR) },
                    MpSearchTypes.PUBLIC.takeIf { chainPermissions.contains(MpUserPermissions.SEARCH_PUBLIC) }
                ).filterNotNull().toMutableSet()
            }
        }
        worker("Копируем поля поиска") {
            dbExerciseFilter.searchStr = requestExerciseFilter.searchStr
            dbExerciseFilter.authorId = requestExerciseFilter.authorId
        }
    }

    repoSearch("Ищем упражнения в БД")

    frontPermissions(title = "Вычисление пользовательских разрешений для фронтенда")

    prepareAnswer("Подготовка ответа")
}).build()