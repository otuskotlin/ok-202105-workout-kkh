package ru.otus.otuskotlin.workout.backend.logics.chains.exercise

import ICorExec
import chain
import handlers.worker
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
import ru.otus.otuskotlin.workout.backend.common.context.CorStatus

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

    chainPermissions("Вычисление разрешений для пользователя")

    worker {
        title = "Инициализация dbExercise"
        on { status == CorStatus.RUNNING }
        handle {
            dbExercise.authorId = principal.id
        }
    }

    accessValidation("Вычисление прав доступа")
    prepareExerciseForSaving("Подготовка объекта упражнения для сохранения")

    repoCreate("Запись объекта в БД")

    worker {
        title = "Подготовка результата к отправке"
        description = title
        on { status == CorStatus.RUNNING }
        handle { responseExercise = dbExercise }
    }

    frontPermissions(title = "Вычисление пользовательских разрешений для фронтенда")

    prepareAnswer("Подготовка ответа")

}).build()
