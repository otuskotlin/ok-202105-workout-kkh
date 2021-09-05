package ru.otus.otuskotlin.workout.backend.logics

import kotlinx.coroutines.runBlocking
import org.junit.Test
import ru.workout.otuskotlin.workout.backend.common.context.BeContext
import ru.workout.otuskotlin.workout.backend.common.context.CorStatus
import ru.workout.otuskotlin.workout.backend.common.models.MpStubCases
import kotlin.test.assertEquals

class WorkoutCrudTest {

    @Test
    fun workoutCreateSuccess() {
        val crud = WorkoutCrud()
        val context = BeContext(
            requestWorkout = WorkoutStub.getModelWorkout(),
            operation = BeContext.MpOperations.CREATE,
            stubCase = MpStubCases.SUCCESS
        )
        runBlocking {
            crud.create(context)
            val expected = WorkoutStub.getModelWorkout()
            assertEquals(CorStatus.SUCCESS, context.status)
            with(context.responseWorkout) {
                assertEquals(expected.workoutDate, workoutDate)
                assertEquals(expected.duration, duration)
                assertEquals(expected.recoveryTime, recoveryTime)
                assertEquals(expected.modificationWorkout, modificationWorkout)
                assertEquals(expected.exercisesBlock, exercisesBlock)
                assertEquals(expected.idWorkout, idWorkout)
                assertEquals(expected.permissions, permissions)
            }
        }
    }

    @Test
    fun workoutReadSuccess() {
        val crud = WorkoutCrud()
        val context = BeContext(
            requestWorkoutId = WorkoutStub.getModelWorkout().idWorkout,
            operation = BeContext.MpOperations.READ,
            stubCase = MpStubCases.SUCCESS
        )
        runBlocking {
            crud.read(context)
            val expected = WorkoutStub.getModelWorkout()
            assertEquals(CorStatus.SUCCESS, context.status)
            with(context.responseWorkout) {
                assertEquals(expected.workoutDate, workoutDate)
                assertEquals(expected.duration, duration)
                assertEquals(expected.recoveryTime, recoveryTime)
                assertEquals(expected.modificationWorkout, modificationWorkout)
                assertEquals(expected.exercisesBlock, exercisesBlock)
                assertEquals(expected.idWorkout, idWorkout)
                assertEquals(expected.permissions, permissions)
            }
        }
    }

    @Test
    fun workoutUpdateSuccess() {
        val crud = WorkoutCrud()
        val context = BeContext(
            operation = BeContext.MpOperations.READ,
            stubCase = MpStubCases.SUCCESS,
            requestWorkout = WorkoutStub.getModelWorkout(),
        )
        runBlocking {
            crud.read(context)
            val expected = WorkoutStub.getModelWorkout()
            assertEquals(CorStatus.SUCCESS, context.status)
            with(context.responseWorkout) {
                assertEquals(expected.workoutDate, workoutDate)
                assertEquals(expected.duration, duration)
                assertEquals(expected.recoveryTime, recoveryTime)
                assertEquals(expected.modificationWorkout, modificationWorkout)
                assertEquals(expected.exercisesBlock, exercisesBlock)
                assertEquals(expected.idWorkout, context.requestWorkout.idWorkout)
                assertEquals(expected.permissions, permissions)
            }
        }
    }

    @Test
    fun workoutDeleteSuccess() {
        val crud = WorkoutCrud()
        val context = BeContext(
            operation = BeContext.MpOperations.DELETE,
            stubCase = MpStubCases.SUCCESS,
            requestWorkoutId = WorkoutStub.getModelWorkout().idWorkout
        )
        runBlocking {
            crud.delete(context)
            val expected = WorkoutStub.getModelWorkout()
            assertEquals(CorStatus.SUCCESS, context.status)
            with(context.responseWorkout) {
                assertEquals(expected.workoutDate, workoutDate)
                assertEquals(expected.duration, duration)
                assertEquals(expected.recoveryTime, recoveryTime)
                assertEquals(expected.modificationWorkout, modificationWorkout)
                assertEquals(expected.exercisesBlock, exercisesBlock)
                assertEquals(expected.idWorkout, idWorkout)
                assertEquals(expected.permissions, permissions)
            }
        }
    }
}