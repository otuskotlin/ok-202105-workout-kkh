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
        }
    }

    @Test
    fun testPostWorkoutRead() {
        val data = ReadWorkoutRequest(
            requestId = "rID:0008",
            debug = Utils.stubDebug
        )
        testPostRequest<ReadWorkoutResponse>(data, "workout/read") {
            assertEquals(ReadWorkoutResponse.Result.SUCCESS, result)
            assertNull(errors)
        }
    }

    @Test
    fun testPostWorkoutUpdate() {
        val data = UpdateWorkoutRequest()
        testPostRequest<UpdateWorkoutResponse>(data, "workout/update") {
            assertEquals(UpdateWorkoutResponse.Result.SUCCESS, result)
            assertNull(errors)
        }
    }

    @Test
    fun testPostWorkoutDelete() {
        val data = DeleteWorkoutRequest()
        testPostRequest<DeleteWorkoutResponse>(data, "workout/delete") {
            assertEquals(DeleteWorkoutResponse.Result.SUCCESS, result)
            assertNull(errors)
        }
    }

    @Test
    fun testPostWorkoutSearch() {
        val data = SearchWorkoutRequest()
        testPostRequest<SearchWorkoutResponse>(data, "workout/search") {

        }
    }

    @Test
    fun testPostWorkoutChainOfExercises() {
        val data = ReadWorkoutRequest()
        testPostRequest<ChainOfExercisesResponse>(data, "workout/chainOfExercises") {
            assertEquals(ChainOfExercisesResponse.Result.SUCCESS, result)
            assertNull(errors)
        }
    }
}