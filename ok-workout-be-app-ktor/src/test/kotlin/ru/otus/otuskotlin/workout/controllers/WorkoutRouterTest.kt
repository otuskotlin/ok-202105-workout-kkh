package ru.otus.otuskotlin.workout.controllers

import org.junit.Test
import ru.otus.otuskotlin.workout.Utils
import ru.otus.otuskotlin.workout.openapi.models.*
import kotlin.test.assertEquals
import kotlin.test.assertNull

class WorkoutRouterTest : RouterTest() {

    @Test
    fun testPostWorkoutInit() {
        val data = InitWorkoutRequest(
            requestId = "rID:0006",
        )
        testPostRequest<InitWorkoutResponse>(data, "workout/init") {
            assertEquals(data.requestId, requestId)
            assertEquals(InitWorkoutResponse.Result.SUCCESS, result)
            assertNull(errors)
        }
    }

    @Test
    fun testPostWorkoutCreate() {
        val data = CreateWorkoutRequest(
            requestId = "rID:0007",
            debug = Utils.stubDebug
        )
        testPostRequest<CreateWorkoutResponse>(data, "workout/create") {
            assertEquals(data.requestId, requestId)
            assertEquals(CreateWorkoutResponse.Result.SUCCESS, result)
            assertNull(errors)
            assertEquals(Utils.stubResponseWorkout, createdWorkout)
        }
    }

    @Test
    fun testPostWorkoutRead() {
        val data = ReadWorkoutRequest(
            requestId = "rID:0008",
            debug = Utils.stubDebug
        )
        testPostRequest<ReadWorkoutResponse>(data, "workout/read") {
            assertEquals(data.requestId, requestId)
            assertEquals(ReadWorkoutResponse.Result.SUCCESS, result)
            assertNull(errors)
            assertEquals(Utils.stubResponseWorkout, readWorkout)
        }
    }

    @Test
    fun testPostWorkoutUpdate() {
        val data = UpdateWorkoutRequest(
            requestId = "rID:0008",
            debug = Utils.stubDebug
        )
        testPostRequest<UpdateWorkoutResponse>(data, "workout/update") {
            assertEquals(data.requestId, requestId)
            assertEquals(UpdateWorkoutResponse.Result.SUCCESS, result)
            assertNull(errors)
            assertEquals(Utils.stubResponseWorkout, updateWorkout)
        }
    }

    @Test
    fun testPostWorkoutDelete() {
        val data = DeleteWorkoutRequest(
            requestId = "rID:0008",
            debug = Utils.stubDebug
        )
        testPostRequest<DeleteWorkoutResponse>(data, "workout/delete") {
            assertEquals(data.requestId, requestId)
            assertEquals(DeleteWorkoutResponse.Result.SUCCESS, result)
            assertNull(errors)
            assertEquals(Utils.stubResponseWorkout, deleteWorkout)
        }
    }

    @Test
    fun testPostWorkoutSearch() {
        val data = SearchWorkoutRequest(
            requestId = "rID:0008",
            debug = Utils.stubDebug
        )
        testPostRequest<SearchWorkoutResponse>(data, "workout/search") {
            assertEquals(data.requestId, requestId)
            assertEquals(SearchWorkoutResponse.Result.SUCCESS, result)
            assertNull(errors)
            assertEquals(Utils.stubResponseSearchWorkout, foundWorkouts)
        }
    }

    @Test
    fun testPostWorkoutChainOfExercises() {
        val data = ReadWorkoutRequest(
            requestId = "rID:0008",
            debug = Utils.stubDebug
        )
        testPostRequest<ChainOfExercisesResponse>(data, "workout/chainOfExercises") {
            assertEquals(data.requestId, requestId)
            assertEquals(ChainOfExercisesResponse.Result.SUCCESS, result)
            assertNull(errors)
            assertEquals(Utils.stubResponseWorkout.exercisesBlock, chainOfExercise)
        }
    }
}