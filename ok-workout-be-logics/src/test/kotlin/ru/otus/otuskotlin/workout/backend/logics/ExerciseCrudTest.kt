package ru.otus.otuskotlin.workout.backend.logics

import kotlinx.coroutines.runBlocking
import org.junit.Test
import ru.workout.otuskotlin.workout.backend.common.context.BeContext
import ru.workout.otuskotlin.workout.backend.common.context.CorStatus
import ru.workout.otuskotlin.workout.backend.common.models.MpStubCases
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class ExerciseCrudTest {

    @Test
    fun exerciseCreateSuccess() {
        val crud = ExerciseCrud()
        val context = BeContext(
            requestExercise = ExerciseStub.getModelExercise(),
            operation = BeContext.MpOperations.CREATE,
            stubCase = MpStubCases.SUCCESS
        )
        runBlocking {
            crud.create(context)
            val expected = ExerciseStub.getModelExercise()
            assertEquals(CorStatus.SUCCESS, context.status)
            with(context.responseExercise) {
                assertEquals(expected.title, title)
                assertEquals(expected.description, description)
                assertEquals(expected.targetMuscleGroup, targetMuscleGroup)
                assertEquals(expected.synergisticMuscleGroup, synergisticMuscleGroup)
                assertEquals(expected.executionTechnique, executionTechnique)
                assertEquals(expected.idExercise, idExercise)
                assertEquals(expected.permissions, permissions)
            }
        }
    }

    @Test
    fun exerciseReadSuccess() {
        val crud = ExerciseCrud()
        val context = BeContext(
            requestExerciseId = ExerciseStub.getModelExercise().idExercise,
            operation = BeContext.MpOperations.READ,
            stubCase = MpStubCases.SUCCESS
        )
        runBlocking {
            crud.read(context)
            val expected = ExerciseStub.getModelExercise()
            assertEquals(CorStatus.SUCCESS, context.status)
            with(context.responseExercise) {
                assertEquals(expected.title, title)
                assertEquals(expected.description, description)
                assertEquals(expected.targetMuscleGroup, targetMuscleGroup)
                assertEquals(expected.synergisticMuscleGroup, synergisticMuscleGroup)
                assertEquals(expected.executionTechnique, executionTechnique)
                assertEquals(expected.idExercise, idExercise)
                assertEquals(expected.permissions, permissions)
            }
        }
    }

    @Test
    fun exerciseDeleteSuccess() {
        val crud = ExerciseCrud()
        val context = BeContext(
            requestExerciseId = ExerciseStub.getModelExercise().idExercise,
            operation = BeContext.MpOperations.DELETE,
            stubCase = MpStubCases.SUCCESS
        )
        runBlocking {
            crud.delete(context)
            val expected = ExerciseStub.getModelExercise()
            assertEquals(CorStatus.SUCCESS, context.status)
            with(context.responseExercise) {
                assertEquals(expected.title, title)
                assertEquals(expected.description, description)
                assertEquals(expected.targetMuscleGroup, targetMuscleGroup)
                assertEquals(expected.synergisticMuscleGroup, synergisticMuscleGroup)
                assertEquals(expected.executionTechnique, executionTechnique)
                assertEquals(expected.idExercise, idExercise)
                assertEquals(expected.permissions, permissions)
            }
        }
    }

    @Test
    fun exerciseSearchSuccess() {
        val crud = ExerciseCrud()
        val context = BeContext(
            operation = BeContext.MpOperations.SEARCH,
            stubCase = MpStubCases.SUCCESS
        )
        runBlocking {
            crud.search(context)
            val expected = ExerciseStub.getModelExercises()
            assertEquals(CorStatus.SUCCESS, context.status)
            assertTrue(expected.size == 2)
        }
    }
}