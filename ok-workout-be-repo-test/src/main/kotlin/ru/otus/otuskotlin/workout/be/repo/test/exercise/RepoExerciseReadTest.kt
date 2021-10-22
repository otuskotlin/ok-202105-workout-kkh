package ru.otus.otuskotlin.workout.be.repo.test.exercise

import kotlinx.coroutines.runBlocking
import ru.otus.otuskotlin.workout.backend.common.models.CommonErrorModel
import ru.otus.otuskotlin.workout.backend.common.models.ExerciseIdModel
import ru.otus.otuskotlin.workout.backend.common.models.ExerciseModel
import ru.otus.otuskotlin.workout.backend.repo.common.exercise.DbExerciseIdRequest
import ru.otus.otuskotlin.workout.backend.repo.common.exercise.IRepoExercise
import java.util.*
import kotlin.test.Test
import kotlin.test.assertEquals

abstract class RepoExerciseReadTest {
    abstract val repo: IRepoExercise

    @Test
    fun readSuccess() {
        val result = runBlocking { repo.read(DbExerciseIdRequest(successId)) }
        assertEquals(true, result.isSuccess)
        assertEquals(readSuccessStub, result.result)
        assertEquals(emptyList(), result.errors)
    }

    @Test
    fun readNotFound() {
        val result = runBlocking { repo.read(DbExerciseIdRequest(notFoundId)) }
        assertEquals(false, result.isSuccess)
        assertEquals(null, result.result)
        assertEquals(
            listOf(CommonErrorModel(field = "id", message = "Not found")),
            result.errors
        )
    }

    companion object : BaseInitExercise() {
        override val initObjects: List<ExerciseModel> = listOf(createInitTestModel("read"))
        private val readSuccessStub = initObjects.first()
        val successId = readSuccessStub.idExercise
        val notFoundId = ExerciseIdModel(UUID.randomUUID())
    }
}