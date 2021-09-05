package ru.otus.otuskotlin.workout.backend.logics

import ru.otus.otuskotlin.workout.backend.logics.chains.workout.*
import ru.workout.otuskotlin.workout.backend.common.context.BeContext

class WorkoutCrud {
    suspend fun create(context: BeContext) {
        WorkoutCreate.exec(context.initSettings())
    }

    suspend fun read(context: BeContext) {
        WorkoutRead.exec(context.initSettings())

    }

    suspend fun update(context: BeContext) {
        WorkoutUpdate.exec(context.initSettings())

    }

    suspend fun delete(context: BeContext) {
//        WorkoutDelete.exec(context.initSettings())
    }

    suspend fun search(context: BeContext) {
//        WorkoutSearch.exec(context.initSettings())

    }

    suspend fun chainOfExercises(context: BeContext) {
//        WorkoutChainOfExercises.exec(context.initSettings())

    }

    private fun BeContext.initSettings() = apply {

    }
}