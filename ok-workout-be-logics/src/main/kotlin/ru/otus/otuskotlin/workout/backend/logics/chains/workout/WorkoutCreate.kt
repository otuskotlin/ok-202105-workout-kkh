package ru.otus.otuskotlin.workout.backend.logics.chains.workout

import ICorExec
import chain
import ru.otus.otuskotlin.workout.backend.logics.chains.stubs.workout.workoutCreateStub
import ru.otus.otuskotlin.workout.backend.logics.helpers.validationLogics
import ru.otus.otuskotlin.workout.backend.logics.workers.chainInitWorker
import ru.otus.otuskotlin.workout.backend.logics.workers.checkOperationWorker
import ru.otus.otuskotlin.workout.backend.logics.workers.prepareAnswer
import ru.otus.otuskotlin.workout.validation.validators.ListNonEmptyValidator
import ru.otus.otuskotlin.workout.validation.validators.StringNonEmptyValidator
import ru.workout.otuskotlin.workout.backend.common.context.BeContext

object WorkoutCreate : ICorExec<BeContext> by chain<BeContext>({
    checkOperationWorker(
        title = "Проверка, что операция соответствует чейну",
        targetOperation = BeContext.MpOperations.CREATE
    )

    chainInitWorker("Инициализация чейна")

    workoutCreateStub(title = "Обработка стабкейса для CREATE")

    // validation
    validationLogics {
        validate<String?> {
            on { requestId }
            validator(StringNonEmptyValidator(field = "requestId"))
        }
        validate<List<*>?> {
            on { requestWorkout.exercisesBlock }
            validator(ListNonEmptyValidator(field = "exercisesBlock"))
        }
    }

    // db working

    prepareAnswer("Подготовка ответа")
}).build()