package ru.otus.otuskotlin.workout.backend.logics

import ExerciseStub
import RepoExerciseInMemory
import kotlinx.coroutines.runBlocking
import ru.workout.otuskotlin.workout.backend.common.context.BeContext
import ru.workout.otuskotlin.workout.backend.common.context.ContextConfig
import ru.workout.otuskotlin.workout.backend.common.context.CorStatus
import ru.workout.otuskotlin.workout.backend.common.models.ExerciseIdModel
import ru.workout.otuskotlin.workout.backend.common.models.WorkMode
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class ExerciseCrudRepoTest {

    @Test
    fun createSuccessTest() {
        val repo = RepoExerciseInMemory()
        val crud = ExerciseCrud(
            config = ContextConfig(
                repoExerciseTest = repo
            )
        )
        val context = BeContext(
            workMode = WorkMode.TEST,
            requestExercise = ExerciseStub.getModelExercise() { idExercise = ExerciseIdModel.NONE },
            operation = BeContext.MpOperations.CREATE
        )
        runBlocking {
            crud.create(context)
        }
        assertEquals(CorStatus.SUCCESS, context.status)
        val expected = ExerciseStub.getModelExercise()
        with(context.responseExercise) {
            assertTrue(idExercise.asString().isNotBlank())
            assertEquals(expected.title, title)
            assertEquals(expected.description, description)
            assertEquals(expected.targetMuscleGroup, targetMuscleGroup)
            assertEquals(expected.synergisticMuscleGroup, synergisticMuscleGroup)
            assertEquals(expected.executionTechnique, executionTechnique)
        }
    }
}