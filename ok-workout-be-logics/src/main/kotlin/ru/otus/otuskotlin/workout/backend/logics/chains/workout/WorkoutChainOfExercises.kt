package ru.otus.otuskotlin.workout.backend.logics.chains.workout

import ICorExec
import chain
import ru.otus.otuskotlin.workout.backend.logics.chains.stubs.workout.workoutChainOfExerciseStub
import ru.otus.otuskotlin.workout.backend.logics.chains.stubs.workout.workoutCreateStub
import ru.otus.otuskotlin.workout.backend.logics.workers.chainInitWorker
import ru.otus.otuskotlin.workout.backend.logics.workers.checkOperationWorker
import ru.otus.otuskotlin.workout.backend.logics.workers.prepareAnswer
import ru.workout.otuskotlin.workout.backend.common.context.BeContext

object WorkoutChainOfExercises : ICorExec<BeContext> by chain<BeContext>({
    checkOperationWorker(
        title = "Проверка, что операция соответствует чейну",
        targetOperation = BeContext.MpOperations.CHAIN_OF_EXERCISES
    )

    chainInitWorker("Инициализация чейна")

    // validation

    workoutChainOfExerciseStub(title = "Обработка стабкейса для CHAIN_OF_EXERCISE")

    // db working

    prepareAnswer("Подготовка ответа")
}).build()