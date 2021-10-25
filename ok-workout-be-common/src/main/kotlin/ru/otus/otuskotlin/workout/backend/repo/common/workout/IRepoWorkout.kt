package ru.otus.otuskotlin.workout.backend.repo.common.workout

interface IRepoWorkout {
    suspend fun create(req: DdWorkoutModelRequest): DbWorkoutModelResponse
    suspend fun read(req: DdWorkoutIdRequest): DbWorkoutModelResponse
    suspend fun update(req: DdWorkoutModelRequest): DbWorkoutModelResponse
    suspend fun delete(req: DdWorkoutIdRequest): DbWorkoutModelResponse
    suspend fun search(req: DbWorkoutFilterRequest): DbWorkoutsModelResponse
}