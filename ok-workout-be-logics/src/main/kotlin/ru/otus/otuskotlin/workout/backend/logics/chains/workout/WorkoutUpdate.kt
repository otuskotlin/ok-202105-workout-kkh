package ru.otus.otuskotlin.workout.backend.logics.chains.workout

import ICorExec
import chain
import ru.otus.otuskotlin.workout.backend.logics.chains.stubs.workout.workoutCreateStub
import ru.otus.otuskotlin.workout.backend.logics.chains.stubs.workout.workoutUpdateStub
import ru.otus.otuskotlin.workout.backend.logics.workers.chainInitWorker
import ru.otus.otuskotlin.workout.backend.logics.workers.checkOperationWorker
import ru.otus.otuskotlin.workout.backend.logics.workers.prepareAnswer
import ru.workout.otuskotlin.workout.backend.common.context.BeContext

object WorkoutUpdate : ICorExec<BeContext> by chain<BeContext>({
    checkOperationWorker(
        title = "Проверка, что операция соответствует чейну",
        targetOperation = BeContext.MpOperations.UPDATE
    )

    chainInitWorker("Инициализация чейна")

    // validation

    workoutUpdateStub(title = "Обработка стабкейса для UPDATE")

    // db working

    prepareAnswer("Подготовка ответа")
}).build()