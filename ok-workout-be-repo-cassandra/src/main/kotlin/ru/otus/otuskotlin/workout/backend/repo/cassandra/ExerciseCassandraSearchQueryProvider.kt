package ru.otus.otuskotlin.workout.backend.repo.cassandra

import ru.otus.otuskotlin.workout.backend.common.context.BeContext
import ru.otus.otuskotlin.workout.backend.common.repo.common.exercise.DbExerciseFilterRequest
import java.util.concurrent.CompletionStage

class ExerciseCassandraSearchQueryProvider(
    private val context: BeContext
) {

    fun search(
        req: DbExerciseFilterRequest
    ): CompletionStage<Collection<ExerciseCassandraDTO>> {
        TODO()
    }
}