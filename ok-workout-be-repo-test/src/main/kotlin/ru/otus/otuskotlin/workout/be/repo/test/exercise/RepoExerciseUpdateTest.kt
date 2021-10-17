package ru.otus.otuskotlin.workout.be.repo.test.exercise

import kotlinx.coroutines.runBlocking
import ru.otus.otuskotlin.workout.backend.common.models.CommonErrorModel
import ru.otus.otuskotlin.workout.backend.common.models.ExerciseIdModel
import ru.otus.otuskotlin.workout.backend.common.models.ExerciseModel
import ru.otus.otuskotlin.workout.backend.common.repo.common.exercise.DbExerciseModelRequest
import ru.otus.otuskotlin.workout.backend.common.repo.common.exercise.IRepoExercise
import java.util.*
import kotlin.test.Test
import kotlin.test.assertEquals

abstract class RepoExerciseUpdateTest {
    abstract val repo: IRepoExercise

    @Test
    fun updateSuccess() {
        val result = runBlocking { repo.update(DbExerciseModelRequest(updateObj)) }
        assertEquals(true, result.isSuccess)
        assertEquals(updateObj, result.result)
        assertEquals(emptyList(), result.errors)
    }

    @Test
    fun updateNotFound() {
        val result = runBlocking { repo.update(DbExerciseModelRequest(updateObjNotFound)) }
        assertEquals(false, result.isSuccess)
        assertEquals(null, result.result)
        assertEquals(listOf(CommonErrorModel(field = "id", message = "Not Found")), result.errors)
    }

    companion object : BaseInitExercise() {
        override val initObjects: List<ExerciseModel> = listOf(createInitTestModel("update"))

        private val updateId = initObjects.first().idExercise
        private val updateIdNotFount = ExerciseIdModel(UUID.randomUUID())

        private val updateObj = ExerciseModel(
            title = "update object",
            description = "update object description",
            targetMuscleGroup = mutableListOf("update target muscle group"),
            synergisticMuscleGroup = mutableListOf("update synergistic muscle group"),
            executionTechnique = "update execution Technique",
            idExercise = updateId
        )

        private val updateObjNotFound = ExerciseModel(
            title = "update object not found",
            description = "update object not found description",
            targetMuscleGroup = mutableListOf("update object not found target muscle group"),
            synergisticMuscleGroup = mutableListOf("update object not found synergistic muscle group"),
            executionTechnique = "update object not found execution Technique",
            idExercise = updateIdNotFount
        )
    }
}