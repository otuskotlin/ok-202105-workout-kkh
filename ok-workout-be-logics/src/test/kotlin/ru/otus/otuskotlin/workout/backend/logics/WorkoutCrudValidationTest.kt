package ru.otus.otuskotlin.workout.backend.logics

import kotlinx.coroutines.runBlocking
import org.junit.Test
import ru.workout.otuskotlin.workout.backend.common.context.BeContext
import ru.workout.otuskotlin.workout.backend.common.context.CorStatus
import ru.workout.otuskotlin.workout.backend.common.models.MpStubCases
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class WorkoutCrudValidationTest {

    @Test
    fun createWorkoutSuccess() {
        val crud = WorkoutCrud()
        val context = BeContext(
            requestId = "rID:00111",
            stubCase = MpStubCases.SUCCESS,
            requestWorkout = WorkoutStub.getModelWorkout(),
            operation = BeContext.MpOperations.CREATE
        )

        runBlocking { crud.create(context) }

        assertEquals(CorStatus.SUCCESS, context.status)
        assertTrue(context.errors.isEmpty())
    }

    @Test
    fun createWorkoutFailing() {
        val crud = WorkoutCrud()
        val context = BeContext(
            requestId = "",
            stubCase = MpStubCases.SUCCESS,
            operation = BeContext.MpOperations.CREATE
        )

        runBlocking { crud.create(context) }

        assertEquals(CorStatus.ERROR, context.status)
        assertEquals(2, context.errors.size)
    }

    @Test
    fun readWorkoutSuccess() {
        val crud = WorkoutCrud()
        val context = BeContext(
            requestId = "rID:00111",
            stubCase = MpStubCases.SUCCESS,
            requestWorkout = WorkoutStub.getModelWorkout(),
            operation = BeContext.MpOperations.READ
        )

        runBlocking { crud.read(context) }

        assertEquals(CorStatus.SUCCESS, context.status)
        assertTrue(context.errors.isEmpty())
    }

    @Test
    fun readWorkoutFailing() {
        val crud = WorkoutCrud()
        val context = BeContext(
            requestId = "",
            stubCase = MpStubCases.SUCCESS,
            operation = BeContext.MpOperations.CREATE
        )

        runBlocking { crud.create(context) }

        assertEquals(CorStatus.ERROR, context.status)
        assertEquals(2, context.errors.size)
    }
}