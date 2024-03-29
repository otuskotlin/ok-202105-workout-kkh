package ru.otus.otuskotlin.workout.backend.logics.chains.exercise

import ICorExec
import chain
import handlers.worker
import ru.otus.otuskotlin.workout.backend.logics.chains.stubs.excercise.exerciseUpdateStub
import ru.otus.otuskotlin.workout.backend.logics.helpers.validationLogics
import ru.otus.otuskotlin.workout.validation.validators.ListNonEmptyValidator
import ru.otus.otuskotlin.workout.validation.validators.StringNonEmptyValidator
import ru.otus.otuskotlin.workout.backend.common.context.BeContext
import ru.otus.otuskotlin.workout.backend.common.context.CorStatus
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

    chainPermissions("Вычисление разрешений для пользователя")
    worker(title = "Инициаизируем requestExerciseId") { requestExerciseId = requestExercise.idExercise }
    repoRead("Читаем объект из БД")
    accessValidation("Вычисление прав доступа")
    prepareExerciseForSaving("Подготовка объекта упражнения для сохранения")

    repoUpdate("Обновляем объект в БД ")

    worker {
        title = "Подготовка результата к отправке"
        description = title
        on { status == CorStatus.RUNNING }
        handle { responseExercise = dbExercise }
    }

    frontPermissions(title = "Вычисление пользовательских разрешений для фронтенда")

    prepareAnswer("Подготовка ответа")
}).build()