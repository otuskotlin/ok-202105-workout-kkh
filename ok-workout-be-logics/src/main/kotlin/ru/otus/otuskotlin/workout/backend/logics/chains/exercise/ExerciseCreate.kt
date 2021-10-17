package ru.otus.otuskotlin.workout.backend.logics.chains.exercise

import ICorExec
import chain
import ru.otus.otuskotlin.workout.backend.logics.chains.stubs.excercise.exerciseCreateStub
import ru.otus.otuskotlin.workout.backend.logics.helpers.validationLogics
import ru.otus.otuskotlin.workout.backend.logics.workers.*
import ru.otus.otuskotlin.workout.backend.logics.workers.chainInitWorker
import ru.otus.otuskotlin.workout.backend.logics.workers.checkOperationWorker
import ru.otus.otuskotlin.workout.backend.logics.workers.chooseDb
import ru.otus.otuskotlin.workout.backend.logics.workers.prepareAnswer
import ru.otus.otuskotlin.workout.validation.validators.ListNonEmptyValidator
import ru.otus.otuskotlin.workout.validation.validators.StringNonEmptyValidator
import ru.otus.otuskotlin.workout.backend.common.context.BeContext

object ExerciseCreate : ICorExec<BeContext> by chain<BeContext>({
    checkOperationWorker(
        title = "Проверка, что операция соответствует чейну",
        targetOperation = BeContext.MpOperations.CREATE
    )

    chainInitWorker("Инициализация чейна")

    chooseDb(title = "Выбираем БД или STUB")

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
        validate<List<*>?> {
            on { requestExercise.targetMuscleGroup }
            validator(ListNonEmptyValidator(field = "targetMuscleGroup"))
        }
        validate<List<*>?> {
            on { requestExercise.synergisticMuscleGroup }
            validator(ListNonEmptyValidator(field = "synergeticMuscleGroup"))
        }
        validate<String?> {
            on { requestExercise.executionTechnique }
            validator(StringNonEmptyValidator(field = "executionTechnique"))
        }
    }

    repoCreate("Запись объекта в БД")

    prepareAnswer("Подготовка ответа")

}).build()
