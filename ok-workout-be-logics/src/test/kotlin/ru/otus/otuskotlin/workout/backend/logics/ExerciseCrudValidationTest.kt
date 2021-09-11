package ru.otus.otuskotlin.workout.backend.logics

import kotlinx.coroutines.runBlocking
import org.junit.Test
import ru.workout.otuskotlin.workout.backend.common.context.BeContext
import ru.workout.otuskotlin.workout.backend.common.context.CorStatus
import ru.workout.otuskotlin.workout.backend.common.models.ExerciseIdModel
import ru.workout.otuskotlin.workout.backend.common.models.MpStubCases
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class ExerciseCrudValidationTest {

    private val crud = ExerciseCrud()

    @Test
    fun createExerciseSuccess() {
        val context = BeContext(
            requestId = "rID:00111",
            stubCase = MpStubCases.SUCCESS,
            requestExercise = ExerciseStub.getModelExercise(),
            operation = BeContext.MpOperations.CREATE
        )

        runBlocking { crud.create(context) }

        assertEquals(CorStatus.SUCCESS, context.status)
        assertTrue(context.errors.isEmpty())
    }

    @Test
    fun createExerciseFailing() {

        val context = BeContext(
            stubCase = MpStubCases.SUCCESS,
            requestExercise = ExerciseStub.getModelExercise() {
                title = ""
                description = ""
                targetMuscleGroup = mutableListOf()
                synergisticMuscleGroup = mutableListOf()
                description = ""
            },
            operation = BeContext.MpOperations.CREATE
        )
        runBlocking { crud.create(context) }

        assertEquals(CorStatus.ERROR, context.status)
        assertEquals(6, context.errors.size)
    }

    @Test
    fun readExerciseSuccess() {
        val context = BeContext(
            requestId = "rID:00111",
            requestExerciseId = ExerciseIdModel("eID:00011"),
            stubCase = MpStubCases.SUCCESS,
            requestExercise = ExerciseStub.getModelExercise(),
            operation = BeContext.MpOperations.READ
        )

        runBlocking { crud.read(context) }

        assertEquals(CorStatus.SUCCESS, context.status)
        assertTrue(context.errors.isEmpty())
    }

    @Test
    fun readExerciseFailing() {

        val context = BeContext(
            stubCase = MpStubCases.SUCCESS,
            requestExercise = ExerciseStub.getModelExercise(),
            operation = BeContext.MpOperations.READ
        )
        runBlocking { crud.read(context) }

        assertEquals(CorStatus.ERROR, context.status)
        assertEquals(2, context.errors.size)
    }
}