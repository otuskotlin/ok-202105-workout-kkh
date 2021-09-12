package ru.otus.otuskotlin.workout.backend.logics.chains.workout

import ICorExec
import chain
import ru.otus.otuskotlin.workout.backend.logics.chains.stubs.workout.workoutChainOfExerciseStub
import ru.otus.otuskotlin.workout.backend.logics.helpers.validationLogics
import ru.otus.otuskotlin.workout.backend.logics.workers.chainInitWorker
import ru.otus.otuskotlin.workout.backend.logics.workers.checkOperationWorker
import ru.otus.otuskotlin.workout.backend.logics.workers.prepareAnswer
import ru.otus.otuskotlin.workout.validation.validators.StringNonEmptyValidator
import ru.workout.otuskotlin.workout.backend.common.context.BeContext

object WorkoutChainOfExercises : ICorExec<BeContext> by chain<BeContext>({
    checkOperationWorker(
        title = "Проверка, что операция соответствует чейну",
        targetOperation = BeContext.MpOperations.CHAIN_OF_EXERCISES
    )

    chainInitWorker("Инициализация чейна")

    workoutChainOfExerciseStub(title = "Обработка стабкейса для CHAIN_OF_EXERCISE")

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