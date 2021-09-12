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

    @Test
    fun updatedWorkoutSuccess() {
        val crud = WorkoutCrud()
        val context = BeContext(
            requestId = "rID:00111",
            stubCase = MpStubCases.SUCCESS,
            requestWorkout = WorkoutStub.getModelWorkout(),
            operation = BeContext.MpOperations.UPDATE
        )

        runBlocking { crud.update(context) }

        assertEquals(CorStatus.SUCCESS, context.status)
        assertTrue(context.errors.isEmpty())
    }

    @Test
    fun updateWorkoutFailing() {
        val crud = WorkoutCrud()
        val context = BeContext(
            requestId = "",
            stubCase = MpStubCases.SUCCESS,
            operation = BeContext.MpOperations.UPDATE
        )

        runBlocking { crud.update(context) }

        assertEquals(CorStatus.ERROR, context.status)
        assertEquals(3, context.errors.size)
    }

    @Test
    fun deleteWorkoutSuccess() {
        val crud = WorkoutCrud()
        val context = BeContext(
            requestId = "rID:00111",
            stubCase = MpStubCases.SUCCESS,
            requestWorkout = WorkoutStub.getModelWorkout(),
            operation = BeContext.MpOperations.DELETE
        )

        runBlocking { crud.delete(context) }

        context.errors.forEach { println(it.message) }

        assertEquals(CorStatus.SUCCESS, context.status)
        assertTrue(context.errors.isEmpty())
    }

    @Test
    fun deleteWorkoutFailing() {
        val crud = WorkoutCrud()
        val context = BeContext(
            requestId = "",
            stubCase = MpStubCases.SUCCESS,
            operation = BeContext.MpOperations.DELETE
        )

        runBlocking { crud.delete(context) }

        assertEquals(CorStatus.ERROR, context.status)
        assertEquals(2, context.errors.size)
    }

    @Test
    fun searchWorkoutSuccess() {
        val crud = WorkoutCrud()
        val context = BeContext(
            requestId = "rID:00111",
            stubCase = MpStubCases.SUCCESS,
            requestWorkout = WorkoutStub.getModelWorkout(),
            operation = BeContext.MpOperations.SEARCH
        )

        runBlocking { crud.search(context) }

        assertEquals(CorStatus.SUCCESS, context.status)
        assertTrue(context.errors.isEmpty())
    }

    @Test
    fun searchWorkoutFailing() {
        val crud = WorkoutCrud()
        val context = BeContext(
            requestId = "",
            stubCase = MpStubCases.SUCCESS,
            operation = BeContext.MpOperations.SEARCH
        )

        runBlocking { crud.search(context) }

        assertEquals(CorStatus.ERROR, context.status)
        assertEquals(1, context.errors.size)
    }

    @Test
    fun chainOfExercisesWorkoutSuccess() {
        val crud = WorkoutCrud()
        val context = BeContext(
            requestId = "rID:00111",
            stubCase = MpStubCases.SUCCESS,
            requestWorkout = WorkoutStub.getModelWorkout(),
            operation = BeContext.MpOperations.CHAIN_OF_EXERCISES
        )

        runBlocking { crud.chainOfExercises(context) }

        assertEquals(CorStatus.SUCCESS, context.status)
        assertTrue(context.errors.isEmpty())
    }

    @Test
    fun chainOfExercisesWorkoutFailing() {
        val crud = WorkoutCrud()
        val context = BeContext(
            requestId = "",
            stubCase = MpStubCases.SUCCESS,
            operation = BeContext.MpOperations.CHAIN_OF_EXERCISES
        )

        runBlocking { crud.chainOfExercises(context) }

        assertEquals(CorStatus.ERROR, context.status)
        assertEquals(2, context.errors.size)
    }
}