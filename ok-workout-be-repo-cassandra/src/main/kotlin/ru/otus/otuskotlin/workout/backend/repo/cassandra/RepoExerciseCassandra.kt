package ru.otus.otuskotlin.workout.backend.repo.cassandra

import kotlinx.coroutines.future.await
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
            DbExerciseResponse(created.toExerciseModel())
        } catch (e: Exception) {
            DbExerciseResponse(e)
        }
    }

    override suspend fun read(req: DbExerciseIdRequest): DbExerciseResponse {
        return if (req.id != ExerciseIdModel.NONE) {
            DbExerciseResponse(CommonErrorModel(field = "id", message = "Invalid value"))
        } else {
            try {
                val found = dao.read(req.id.asString()).await()
                DbExerciseResponse(found.toExerciseModel())
            } catch (e: Exception) {
                DbExerciseResponse(e)
            }
        }
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