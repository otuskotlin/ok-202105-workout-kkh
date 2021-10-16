package ru.otus.otuskotlin.workout.be.repo.test.exercise

import kotlinx.coroutines.runBlocking
import ru.workout.otuskotlin.workout.backend.common.models.ExerciseModel
import ru.workout.otuskotlin.workout.backend.common.repo.common.exercise.DbExerciseFilterRequest
import ru.workout.otuskotlin.workout.backend.common.repo.common.exercise.IRepoExercise
import kotlin.test.Test
import kotlin.test.assertEquals

abstract class RepoExerciseSearchTest {
    abstract val repo: IRepoExercise

    @Test
    fun searchTitle() {
        val result = runBlocking { repo.search(DbExerciseFilterRequest("приседания")) }
        assertEquals(true, result.isSuccess)
        val expected = listOf(initObjects[0], initObjects[1], initObjects[2])
        assertEquals(
            expected.sortedBy { it.idExercise.asString() },
            result.result.sortedBy { it.idExercise.asString() })
        assertEquals(emptyList(), result.errors)
    }

    @Test
    fun searchTargetMuscleGroup() {

    }

    companion object : BaseInitExercise() {
        override val initObjects: List<ExerciseModel> = listOf(
            createInitTestModel("exercise 1", title = "Приседания со штангой"),
            createInitTestModel("exercise 2", description = "Как приседания"),
            createInitTestModel("exercise 3", title = "Глубокие приседания"),
            createInitTestModel("exercise 4")
        )
    }
}