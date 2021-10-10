package ru.workout.otuskotlin.workout.backend.common.repo.workout

interface IRepoWorkout {
    suspend fun create(req: DdWorkoutModelRequest): DbWorkoutModelResponse
    suspend fun read(req: DdWorkoutIdRequest): DbWorkoutModelResponse
    suspend fun update(req: DdWorkoutModelRequest): DbWorkoutModelResponse
    suspend fun delete(req: DdWorkoutIdRequest): DbWorkoutModelResponse
    suspend fun search(req: DbWorkoutFilterRequest): DbWorkoutsModelResponse
}