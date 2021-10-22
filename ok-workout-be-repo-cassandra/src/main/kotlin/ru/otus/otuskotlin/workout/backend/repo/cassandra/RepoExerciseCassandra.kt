package ru.otus.otuskotlin.workout.backend.repo.cassandra

import kotlinx.coroutines.future.await
import kotlinx.coroutines.withTimeout
import ru.otus.otuskotlin.workout.backend.common.models.CommonErrorModel
import ru.otus.otuskotlin.workout.backend.common.models.ExerciseIdModel
import ru.otus.otuskotlin.workout.backend.common.repo.common.exercise.*
import java.util.*

class RepoExerciseCassandra(
    private val dao: ExerciseCassandraDAO
) : IRepoExercise {
    override suspend fun create(req: DbExerciseModelRequest): DbExerciseResponse {
        val exercise = req.exercise.copy(idExercise = ExerciseIdModel(UUID.randomUUID()))
        return try {
            dao.create(ExerciseCassandraDTO(exercise)).await()
            val created = dao.read(exercise.idExercise.asString()).await()
            if (created == null) {
                DbExerciseResponse(CommonErrorModel(field = "id", message = "Not found"))
            } else {
                DbExerciseResponse(created.toExerciseModel())
            }
        } catch (e: Exception) {
            println("catch: ${e.message}")
            DbExerciseResponse(e)
        }
    }

    override suspend fun read(req: DbExerciseIdRequest): DbExerciseResponse {
        return if (req.id == ExerciseIdModel.NONE) {
            DbExerciseResponse(CommonErrorModel(field = "id", message = "Invalid value"))
        } else {
            try {
                val found = dao.read(req.id.asString()).await()
                if (found == null) {
                    DbExerciseResponse(CommonErrorModel(field = "id", message = "Not found"))
                } else {
                    DbExerciseResponse(found.toExerciseModel())
                }
            } catch (e: Exception) {
                DbExerciseResponse(e)
            }
        }
    }

    override suspend fun update(req: DbExerciseModelRequest): DbExerciseResponse {
        return if (req.exercise.idExercise == ExerciseIdModel.NONE) {
            DbExerciseResponse(CommonErrorModel(field = "idExercise", message = "Invalid value"))
        } else try {
            dao.update(ExerciseCassandraDTO(req.exercise)).await()
            val updated = dao.read(req.exercise.idExercise.asString()).await()
            if (updated == null) {
                DbExerciseResponse(CommonErrorModel(field = "id", message = "Not found"))
            } else {
                DbExerciseResponse(updated.toExerciseModel())
            }
        } catch (e: Exception) {
            DbExerciseResponse(e)
        }
    }

    override suspend fun delete(req: DbExerciseIdRequest): DbExerciseResponse {
        return if (req.id == ExerciseIdModel.NONE) {
            DbExerciseResponse(CommonErrorModel(field = "id", message = "Invalid value"))
        } else {
            try {
                val deleted = dao.read(req.id.asString()).await()?.also {
                    dao.delete(it)
                }
                if (deleted == null) {
                    DbExerciseResponse(CommonErrorModel(field = "id", message = "Not found"))
                } else {
                    DbExerciseResponse(deleted.toExerciseModel())
                }
            } catch (e: Exception) {
                DbExerciseResponse(e)
            }
        }
    }

    override suspend fun search(req: DbExerciseFilterRequest): DbExercisesResponse {
        return try {
            val found = dao.search(req).await().map { it.toExerciseModel() }
            DbExercisesResponse(true, emptyList(), found)
        } catch (e: Exception) {
            DbExercisesResponse(false, listOf(CommonErrorModel(e)), emptyList())
        }

    }
}