package ru.otus.otuskotlin.workout.backend.logics

import ru.otus.otuskotlin.workout.backend.logics.chains.exercise.ExerciseCreate
import ru.otus.otuskotlin.workout.backend.logics.chains.exercise.ExerciseDelete
import ru.otus.otuskotlin.workout.backend.logics.chains.exercise.ExerciseRead
import ru.workout.otuskotlin.workout.backend.common.context.BeContext

class ExerciseCrud {
    suspend fun create(context: BeContext) {
        ExerciseCreate.exec(context.initSettings())
    }

    suspend fun read(context: BeContext) {
        ExerciseRead.exec(context.initSettings())

    }

    suspend fun update(context: BeContext) {

    }

    suspend fun delete(context: BeContext) {
        ExerciseDelete.exec(context.initSettings())
    }

    suspend fun search(context: BeContext) {

    }

    private fun BeContext.initSettings() = apply {

    }
}