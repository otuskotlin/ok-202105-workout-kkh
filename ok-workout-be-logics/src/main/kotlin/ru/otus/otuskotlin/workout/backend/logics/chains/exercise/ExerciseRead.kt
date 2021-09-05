package ru.otus.otuskotlin.workout.backend.logics.chains.exercise

import ICorExec
import chain
import ru.otus.otuskotlin.workout.backend.logics.chains.stubs.excercise.exerciseReadStub
import ru.otus.otuskotlin.workout.backend.logics.workers.chainInitWorker
import ru.otus.otuskotlin.workout.backend.logics.workers.checkOperationWorker
import ru.otus.otuskotlin.workout.backend.logics.workers.prepareAnswer
import ru.workout.otuskotlin.workout.backend.common.context.BeContext

object ExerciseRead : ICorExec<BeContext> by chain<BeContext>({
    checkOperationWorker(
        title = "Проверка, что операция соответствует чейну",
        targetOperation = BeContext.MpOperations.READ
    )

    chainInitWorker("Инициализация чейна")

    exerciseReadStub(title = "Обработка стабкейса для READ")

    prepareAnswer("Подготовка ответа")
}).build()