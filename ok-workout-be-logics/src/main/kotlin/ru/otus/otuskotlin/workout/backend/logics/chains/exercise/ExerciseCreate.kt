package ru.otus.otuskotlin.workout.backend.logics.chains.exercise

import ICorExec
import chain
import ru.otus.otuskotlin.workout.backend.logics.chains.stubs.excercise.exerciseCreateStub
import ru.otus.otuskotlin.workout.backend.logics.helpers.validationLogics
import ru.otus.otuskotlin.workout.backend.logics.workers.chainInitWorker
import ru.otus.otuskotlin.workout.backend.logics.workers.checkOperationWorker
import ru.otus.otuskotlin.workout.backend.logics.workers.prepareAnswer
import ru.otus.otuskotlin.workout.validation.validators.StringNonEmptyValidator
import ru.workout.otuskotlin.workout.backend.common.context.BeContext

object ExerciseCreate : ICorExec<BeContext> by chain<BeContext>({
    checkOperationWorker(
        title = "Проверка, что операция соответствует чейну",
        targetOperation = BeContext.MpOperations.CREATE
    )

    chainInitWorker("Инициализация чейна")

    exerciseCreateStub(title = "Обработка стабкейса для CREATE")

    validationLogics {
        validate<String?> {
            on { requestExercise.title }
            validator(StringNonEmptyValidator(field = "title"))
        }
        validate<String?> {
            on { requestExercise.description }
            validator(StringNonEmptyValidator(field = "description"))
        }
    }

    // db working

    prepareAnswer("Подготовка ответа")

}).build()
