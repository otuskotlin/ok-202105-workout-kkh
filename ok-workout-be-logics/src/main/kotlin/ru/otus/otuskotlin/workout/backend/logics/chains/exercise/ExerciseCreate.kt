package ru.otus.otuskotlin.workout.backend.logics.chains.exercise

import ICorExec
import chain
import ru.otus.otuskotlin.workout.backend.logics.chains.stubs.excercise.exerciseCreateStub
import ru.otus.otuskotlin.workout.backend.logics.workers.chainInit
import ru.otus.otuskotlin.workout.backend.logics.workers.checkOperation
import ru.otus.otuskotlin.workout.backend.logics.workers.prepareAnswer
import ru.workout.otuskotlin.workout.backend.common.context.BeContext

object ExerciseCreate : ICorExec<BeContext> by chain<BeContext>({
    checkOperation(
        title = "Проверка, что операция соответствует чейну",
        targetOperation = BeContext.MpOperations.CREATE
    )

    chainInit("Инициализация чейна")

    // validation

    exerciseCreateStub(title = "Обработка стабкейсов")

    // db working

    prepareAnswer("Подготовка ответа")

}).build()
