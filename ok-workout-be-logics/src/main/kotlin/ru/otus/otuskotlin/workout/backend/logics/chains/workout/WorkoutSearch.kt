package ru.otus.otuskotlin.workout.backend.logics.chains.workout

import ICorExec
import chain
import ru.otus.otuskotlin.workout.backend.logics.chains.stubs.workout.workoutSearchStub
import ru.otus.otuskotlin.workout.backend.logics.helpers.validationLogics
import ru.otus.otuskotlin.workout.backend.logics.workers.chainInitWorker
import ru.otus.otuskotlin.workout.backend.logics.workers.checkOperationWorker
import ru.otus.otuskotlin.workout.backend.logics.workers.prepareAnswer
import ru.otus.otuskotlin.workout.validation.validators.StringNonEmptyValidator
import ru.workout.otuskotlin.workout.backend.common.context.BeContext

object WorkoutSearch : ICorExec<BeContext> by chain<BeContext>({
    checkOperationWorker(
        title = "Проверка, что операция соответствует чейну",
        targetOperation = BeContext.MpOperations.SEARCH
    )

    chainInitWorker("Инициализация чейна")

    workoutSearchStub(title = "Обработка стабкейса для Search")

    // validation
    validationLogics {
        validate<String?> {
            on { requestId }
            validator(StringNonEmptyValidator(field = "requestId"))
        }
    }

    // db working

    prepareAnswer("Подготовка ответа")
}).build()