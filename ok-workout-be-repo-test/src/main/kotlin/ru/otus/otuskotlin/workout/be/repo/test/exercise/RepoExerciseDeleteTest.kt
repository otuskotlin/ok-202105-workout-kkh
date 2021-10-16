package ru.otus.otuskotlin.workout.be.repo.test.exercise

import kotlinx.coroutines.runBlocking
import ru.workout.otuskotlin.workout.backend.common.models.CommonErrorModel
import ru.workout.otuskotlin.workout.backend.common.models.ExerciseIdModel
import ru.workout.otuskotlin.workout.backend.common.models.ExerciseModel
import ru.workout.otuskotlin.workout.backend.common.repo.common.exercise.DbExerciseIdRequest
import ru.workout.otuskotlin.workout.backend.common.repo.common.exercise.IRepoExercise
import java.util.*
import kotlin.test.Test
import kotlin.test.assertEquals

abstract class RepoExerciseDeleteTest {
    abstract val repo: IRepoExercise

    @Test
    fun deleteSuccess() {
        val result = runBlocking { repo.delete(DbExerciseIdRequest(successId)) }
        assertEquals(true, result.isSuccess)
        assertEquals(deleteSuccessStub, result.result)
        assertEquals(emptyList(), result.errors)
    }

    @Test
    fun deleteNotFound() {
        val result = runBlocking { repo.delete(DbExerciseIdRequest(notFoundId)) }
        assertEquals(false, result.isSuccess)
        assertEquals(null, result.result)
        assertEquals(
            listOf(
                CommonErrorModel(
                    field = "id",
                    message = "Not Found"
                )
            ),
            result.errors
        )
    }

    companion object : BaseInitExercise() {
        override val initObjects: List<ExerciseModel> = listOf(createInitTestModel("delete"))
        private val deleteSuccessStub = initObjects.first()
        val successId = deleteSuccessStub.idExercise
        val notFoundId = ExerciseIdModel(UUID.randomUUID())
    }

}