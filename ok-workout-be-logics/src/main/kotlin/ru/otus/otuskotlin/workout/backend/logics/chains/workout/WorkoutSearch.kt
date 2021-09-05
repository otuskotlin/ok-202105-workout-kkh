package ru.otus.otuskotlin.workout.backend.logics.chains.workout

import ICorExec
import chain
import ru.otus.otuskotlin.workout.backend.logics.chains.stubs.workout.workoutSearchStub
import ru.otus.otuskotlin.workout.backend.logics.workers.chainInitWorker
import ru.otus.otuskotlin.workout.backend.logics.workers.checkOperationWorker
import ru.otus.otuskotlin.workout.backend.logics.workers.prepareAnswer
import ru.workout.otuskotlin.workout.backend.common.context.BeContext

object WorkoutSearch : ICorExec<BeContext> by chain<BeContext>({
    checkOperationWorker(
        title = "Проверка, что операция соответствует чейну",
        targetOperation = BeContext.MpOperations.SEARCH
    )

    chainInitWorker("Инициализация чейна")

    // validation

    workoutSearchStub(title = "Обработка стабкейса для Search")

    // db working

    prepareAnswer("Подготовка ответа")
}).build()