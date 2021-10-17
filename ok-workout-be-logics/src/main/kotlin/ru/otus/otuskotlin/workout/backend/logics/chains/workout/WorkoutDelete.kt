package ru.otus.otuskotlin.workout.backend.logics.chains.workout

import ICorExec
import chain
import ru.otus.otuskotlin.workout.backend.logics.chains.stubs.workout.workoutDeleteStub
import ru.otus.otuskotlin.workout.backend.logics.helpers.validationLogics
import ru.otus.otuskotlin.workout.backend.logics.workers.chainInitWorker
import ru.otus.otuskotlin.workout.backend.logics.workers.checkOperationWorker
import ru.otus.otuskotlin.workout.backend.logics.workers.prepareAnswer
import ru.otus.otuskotlin.workout.validation.validators.StringNonEmptyValidator
import ru.otus.otuskotlin.workout.backend.common.context.BeContext

object WorkoutDelete : ICorExec<BeContext> by chain<BeContext>({
    checkOperationWorker(
        title = "Проверка, что операция соответствует чейну",
        targetOperation = BeContext.MpOperations.DELETE
    )

    chainInitWorker("Инициализация чейна")

    workoutDeleteStub(title = "Обработка стабкейса для DELETE")

    // validation
    validationLogics {
        validate<String?> {
            on { requestId }
            validator(StringNonEmptyValidator(field = "requestId"))
        }
        validate<String?> {
            on { requestWorkoutId.asString() }
            validator(StringNonEmptyValidator(field = "requestWorkoutId"))
        }
    }

    // db working

    prepareAnswer("Подготовка ответа")
}).build()