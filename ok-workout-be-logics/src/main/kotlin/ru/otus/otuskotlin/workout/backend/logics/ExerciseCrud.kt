package ru.otus.otuskotlin.workout.backend.logics

import ru.otus.otuskotlin.workout.backend.logics.chains.exercise.*
import ru.workout.otuskotlin.workout.backend.common.context.BeContext
import ru.workout.otuskotlin.workout.backend.common.context.ContextConfig

class ExerciseCrud(val config: ContextConfig = ContextConfig()) {
    suspend fun create(context: BeContext) {
        ExerciseCreate.exec(context.initSettings())
    }

    suspend fun read(context: BeContext) {
        ExerciseRead.exec(context.initSettings())

    }

    suspend fun update(context: BeContext) {
        ExerciseUpdate.exec(context.initSettings())

    }

    suspend fun delete(context: BeContext) {
        ExerciseDelete.exec(context.initSettings())
    }

    suspend fun search(context: BeContext) {
        ExerciseSearch.exec(context.initSettings())

    }

    private fun BeContext.initSettings() = apply {
        config = this@ExerciseCrud.config
    }
}