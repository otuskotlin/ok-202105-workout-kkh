package ru.otus.otuskotlin.workout.backend.logics.chains.exercise

import ICorExec
import chain
import ru.otus.otuskotlin.workout.backend.logics.chains.stubs.excercise.exerciseUpdateStub
import ru.otus.otuskotlin.workout.backend.logics.helpers.validationLogics
import ru.otus.otuskotlin.workout.validation.validators.ListNonEmptyValidator
import ru.otus.otuskotlin.workout.validation.validators.StringNonEmptyValidator
import ru.otus.otuskotlin.workout.backend.common.context.BeContext
import ru.otus.otuskotlin.workout.backend.logics.workers.*
import ru.otus.otuskotlin.workout.backend.logics.workers.chainInitWorker
import ru.otus.otuskotlin.workout.backend.logics.workers.checkOperationWorker
import ru.otus.otuskotlin.workout.backend.logics.workers.chooseDb
import ru.otus.otuskotlin.workout.backend.logics.workers.prepareAnswer

object ExerciseUpdate : ICorExec<BeContext> by chain<BeContext>({
    checkOperationWorker(
        title = "Проверка, что операция соответствует чейну",
        targetOperation = BeContext.MpOperations.UPDATE
    )

    chainInitWorker("Инициализация чейна")

    chooseDb(title = "Выбираем БД или STUB")

    exerciseUpdateStub(title = "Обработка стабкейса для UPDATE")

    // validation
    validationLogics {

        validate<String?> {
            on { requestId }
            validator(StringNonEmptyValidator(field = "requestId"))
        }

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

        validate<String?> {
            on { requestExercise.idExercise.asString() }
            validator(StringNonEmptyValidator(field = "idExercise"))
        }
    }

    repoUpdate("Обновляем объект в БД ")

    prepareAnswer("Подготовка ответа")
}).build()