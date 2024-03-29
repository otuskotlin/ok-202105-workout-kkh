package ru.otus.otuskotlin.workout.backend.logics.chains.exercise

import ICorExec
import chain
import handlers.worker
import ru.otus.otuskotlin.workout.backend.logics.chains.stubs.excercise.exerciseDeleteStub
import ru.otus.otuskotlin.workout.backend.logics.helpers.validationLogics
import ru.otus.otuskotlin.workout.validation.validators.StringNonEmptyValidator
import ru.otus.otuskotlin.workout.backend.common.context.BeContext
import ru.otus.otuskotlin.workout.backend.common.context.CorStatus
import ru.otus.otuskotlin.workout.backend.logics.workers.*
import ru.otus.otuskotlin.workout.backend.logics.workers.chainInitWorker
import ru.otus.otuskotlin.workout.backend.logics.workers.checkOperationWorker
import ru.otus.otuskotlin.workout.backend.logics.workers.chooseDb
import ru.otus.otuskotlin.workout.backend.logics.workers.prepareAnswer

object ExerciseDelete : ICorExec<BeContext> by chain<BeContext>({
    checkOperationWorker(
        title = "Проверка, что операция соответствует чейну",
        targetOperation = BeContext.MpOperations.DELETE
    )

    chainInitWorker("Инициализация чейна")

    chooseDb(title = "Выбираем БД или STUB")

    exerciseDeleteStub(title = "Обработка стабкейса для DELETE")

    // validation

    validationLogics {
        validate<String?> {
            on { requestExerciseId.asString() }
            validator(StringNonEmptyValidator(field = "requestId"))
        }
    }

    chainPermissions("Вычисление разрешений для пользователя")
    repoRead("Читаем объект из БД")
    accessValidation("Вычисление прав доступа")
    repoDelete("Удаление объекта из БД")

    worker {
        title = "Подготовка результата к отправке"
        description = title
        on { status == CorStatus.RUNNING }
        handle { responseExercise = dbExercise }
    }

    frontPermissions(title = "Вычисление пользовательских разрешений для фронтенда")

    prepareAnswer("Подготовка ответа")
}).build()