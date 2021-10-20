package ru.otus.otuskotlin.workout.backend.repo.cassandra

import ru.otus.otuskotlin.workout.backend.common.repo.common.exercise.*

class RepoExerciseCassandra : IRepoExercise {
    override suspend fun create(req: DbExerciseModelRequest): DbExerciseResponse {
        TODO("Not yet implemented")
    }

    override suspend fun read(req: DbExerciseIdRequest): DbExerciseResponse {
        TODO("Not yet implemented")
    }

    override suspend fun update(req: DbExerciseModelRequest): DbExerciseResponse {
        TODO("Not yet implemented")
    }

    override suspend fun delete(req: DbExerciseIdRequest): DbExerciseResponse {
        TODO("Not yet implemented")
    }

    override suspend fun search(req: DbExerciseFilterRequest): DbExercisesResponse {
        TODO("Not yet implemented")
    }
}