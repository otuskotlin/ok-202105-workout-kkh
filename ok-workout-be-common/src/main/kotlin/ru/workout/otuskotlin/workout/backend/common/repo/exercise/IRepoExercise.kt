package ru.workout.otuskotlin.workout.backend.common.repo.exercise

import ru.workout.otuskotlin.workout.backend.common.repo.exercise.DbExerciseFilterRequest
import ru.workout.otuskotlin.workout.backend.common.repo.exercise.DbExerciseIdRequest
import ru.workout.otuskotlin.workout.backend.common.repo.exercise.DbExerciseModelRequest
import ru.workout.otuskotlin.workout.backend.common.repo.exercise.DbExerciseResponse

interface IRepoExercise {
    suspend fun create(req: DbExerciseModelRequest): DbExerciseResponse
    suspend fun read(req: DbExerciseIdRequest): DbExerciseResponse
    suspend fun update(req: DbExerciseModelRequest): DbExerciseResponse
    suspend fun delete(req: DbExerciseIdRequest): DbExerciseResponse
    suspend fun search(req: DbExerciseFilterRequest): DbExerciseResponse
}