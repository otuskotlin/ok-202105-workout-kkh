package ru.otus.otuskotlin.workout.backend.logics.chains.exercise

import ICorExec
import chain
import ru.otus.otuskotlin.workout.backend.logics.chains.stubs.excercise.exerciseSearchStub
import ru.otus.otuskotlin.workout.backend.logics.workers.chainInitWorker
import ru.otus.otuskotlin.workout.backend.logics.workers.checkOperationWorker
import ru.otus.otuskotlin.workout.backend.logics.workers.prepareAnswer
import ru.workout.otuskotlin.workout.backend.common.context.BeContext

object ExerciseSearch : ICorExec<BeContext> by chain<BeContext>({
    checkOperationWorker(
        title = "Проверка, что операция соответствует чейну",
        targetOperation = BeContext.MpOperations.SEARCH
    )

    chainInitWorker("Инициализация чейна")

    // validation

    exerciseSearchStub(title = "Обработка стабкейса для SEARCH")

    // db working

    prepareAnswer("Подготовка ответа")
}).build()