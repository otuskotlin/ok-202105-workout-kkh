package ru.workout.otuskotlin.workout.backend.common.repo.common.exercise

interface IRepoExercise {
    suspend fun create(req: DbExerciseModelRequest): DbExerciseResponse
    suspend fun read(req: DbExerciseIdRequest): DbExerciseResponse
    suspend fun update(req: DbExerciseModelRequest): DbExerciseResponse
    suspend fun delete(req: DbExerciseIdRequest): DbExerciseResponse
    suspend fun search(req: DbExerciseFilterRequest): DbExercisesResponse

    object NONE: IRepoExercise {
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
}