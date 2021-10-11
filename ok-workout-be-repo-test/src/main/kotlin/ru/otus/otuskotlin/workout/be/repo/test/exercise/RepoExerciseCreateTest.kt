package ru.otus.otuskotlin.workout.be.repo.test.exercise

import kotlinx.coroutines.runBlocking
import org.junit.Test
import ru.workout.otuskotlin.workout.backend.common.models.ExerciseIdModel
import ru.workout.otuskotlin.workout.backend.common.models.ExerciseModel
import ru.workout.otuskotlin.workout.backend.common.repo.common.exercise.DbExerciseModelRequest
import ru.workout.otuskotlin.workout.backend.common.repo.common.exercise.IRepoExercise
import kotlin.test.assertEquals

abstract class RepoExerciseCreateTest {
    abstract val repo: IRepoExercise

    @Test
    fun createSuccess() {
        val result = runBlocking { repo.create(DbExerciseModelRequest(createObj)) }
        val expected = createObj.copy(idExercise = result.result?.idExercise ?: ExerciseIdModel.NONE)
        assertEquals(expected, result.result)
    }

    companion object : BaseInitExercise() {
        private val createObj = ExerciseModel(
            title = "create object",
            description = "create object description",
            targetMuscleGroup = mutableListOf("create object target muscle group"),
            synergisticMuscleGroup = mutableListOf("create object synergistic muscle group"),
            executionTechnique = "create object execution technique"
        )

        override val initObjects: List<ExerciseModel> = emptyList()
    }
}