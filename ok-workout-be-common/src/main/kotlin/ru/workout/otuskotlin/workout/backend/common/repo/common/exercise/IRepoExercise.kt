package ru.workout.otuskotlin.workout.backend.common.repo.common.exercise

interface IRepoExercise {
    suspend fun create(req: DbExerciseModelRequest): DbExerciseResponse
    suspend fun read(req: DbExerciseIdRequest): DbExerciseResponse
    suspend fun update(req: DbExerciseModelRequest): DbExerciseResponse
    suspend fun delete(req: DbExerciseIdRequest): DbExerciseResponse
    suspend fun search(req: DbExerciseFilterRequest): DbExercisesResponse
}