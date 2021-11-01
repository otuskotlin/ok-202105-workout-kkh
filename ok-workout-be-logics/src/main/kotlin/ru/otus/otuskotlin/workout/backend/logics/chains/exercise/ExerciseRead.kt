package ru.otus.otuskotlin.workout.backend.logics.chains.exercise

import ICorExec
import chain
import handlers.worker
import ru.otus.otuskotlin.workout.backend.logics.chains.stubs.excercise.exerciseReadStub
import ru.otus.otuskotlin.workout.backend.logics.helpers.validationLogics
import ru.otus.otuskotlin.workout.validation.validators.StringNonEmptyValidator
import ru.otus.otuskotlin.workout.backend.common.context.BeContext
import ru.otus.otuskotlin.workout.backend.common.context.CorStatus
import ru.otus.otuskotlin.workout.backend.logics.workers.*
import ru.otus.otuskotlin.workout.backend.logics.workers.chainInitWorker
import ru.otus.otuskotlin.workout.backend.logics.workers.checkOperationWorker
import ru.otus.otuskotlin.workout.backend.logics.workers.chooseDb
import ru.otus.otuskotlin.workout.backend.logics.workers.prepareAnswer

object ExerciseRead : ICorExec<BeContext> by chain<BeContext>({
    checkOperationWorker(
        title = "Проверка, что операция соответствует чейну",
        targetOperation = BeContext.MpOperations.READ
    )

    chainInitWorker("Инициализация чейна")

    chooseDb(title = "Выбираем БД или STUB")

    exerciseReadStub(title = "Обработка стабкейса для READ")

    // validation
    validationLogics {
        validate<String?> {
            on { requestExerciseId.asString() }
            validator(StringNonEmptyValidator(field = "requestExerciseId"))
        }
    }

    chainPermissions("Вычисление разрешений для пользователя")
    repoRead("Читаем объект из БД")
    accessValidation("Вычисление прав доступа")

    worker {
        title = "Подготовка результата к отправке"
        description = title
        on { status == CorStatus.RUNNING }
        handle { responseExercise = dbExercise }
    }

    frontPermissions(title = "Вычисление пользовательских разрешений для фронтенда")

    prepareAnswer("Подготовка ответа")
}).build()