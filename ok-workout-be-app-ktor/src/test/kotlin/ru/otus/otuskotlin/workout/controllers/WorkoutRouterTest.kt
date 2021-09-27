package ru.otus.otuskotlin.workout.controllers

import org.junit.Test
import ru.otus.otuskotlin.workout.Utils
import ru.otus.otuskotlin.workout.Utils.stubUpdatableWorkout
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
            debug = Utils.stubDebugSuccess,
            createWorkout = Utils.stubCreatableWorkout
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
            debug = Utils.stubDebugSuccess,
            readWorkoutId = WorkoutStub.getModelWorkout().idWorkout.asString()
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
            debug = Utils.stubDebugSuccess,
            updateWorkout = stubUpdatableWorkout
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
            debug = Utils.stubDebugSuccess,
            deleteWorkoutId = WorkoutStub.getModelWorkout().idWorkout.asString()
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
            date = "2021-08-23T14:00:00.0Z",
            requestId = "rID:0008",
            debug = Utils.stubDebugSuccess,
            searchMuscleGroup = "Квадрицепсы"
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
        val data = ChainOfExercisesRequest(
            requestId = "rID:0008",
            debug = Utils.stubDebugSuccess,
            readWorkoutId = WorkoutStub.getModelWorkout().idWorkout.asString()
        )
        testPostRequest<ChainOfExercisesResponse>(data, "workout/chainOfExercises") {
            assertEquals(data.requestId, requestId)
            assertEquals(ChainOfExercisesResponse.Result.SUCCESS, result)
            assertNull(errors)
            assertEquals(Utils.stubResponseWorkout.exercisesBlock, chainOfExercise)
        }
    }
}