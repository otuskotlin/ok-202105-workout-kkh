package ru.otus.otuskotlin.workout.controllers

import org.junit.Test
import ru.otus.otuskotlin.workout.Utils
import ru.otus.otuskotlin.workout.Utils.stubCreatableExercise
import ru.otus.otuskotlin.workout.Utils.stubResponseExercise
import ru.otus.otuskotlin.workout.Utils.stubUpdatableExercise
import ru.otus.otuskotlin.workout.openapi.models.*
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull

class ExerciseRouterTest : RouterTest() {

    @Test
    fun testPostExerciseInit() {
        val data = InitExerciseRequest(
            requestId = "rID:0000"
        )
        testPostRequest<InitExerciseResponse>(data, "exercise/init") {
            assertEquals(data.requestId, requestId)
            assertEquals(InitExerciseResponse.Result.SUCCESS, result)
            assertNull(errors)
        }
    }

    @Test
    fun testPostExerciseCreate() {
        val data = CreateExerciseRequest(
            requestId = "rID:0001",
            createExercise = stubCreatableExercise,
            debug = Utils.stubDebugSuccess
        )

        testPostRequest<CreateExerciseResponse>(data, "exercise/create") {
            val expected = stubResponseExercise.copy(id = createdExercise?.id)
            assertEquals(data.requestId, requestId)
            assertEquals(CreateExerciseResponse.Result.SUCCESS, result)
            assertNull(errors)
            assertEquals(expected, createdExercise)
        }
    }

    @Test
    fun testPostExerciseRead() {
        val data = ReadExerciseRequest(
            requestId = "rID:0002",
            debug = Utils.stubDebugSuccess,
            readExerciseId = ExerciseStub.getModelExercise().idExercise.asString()
        )

        testPostRequest<ReadExerciseResponse>(data, "exercise/read") {
            assertEquals(data.requestId, requestId)
            assertEquals(ReadExerciseResponse.Result.SUCCESS, result)
            assertNull(errors)
            assertEquals(stubResponseExercise, readExercise)
        }
    }

    @Test
    fun testPostExerciseUpdate() {
        val data = UpdateExerciseRequest(
            requestId = "rID:0003",
            debug = Utils.stubDebugSuccess,
            updateExercise = stubUpdatableExercise
        )

        testPostRequest<UpdateExerciseResponse>(data, "exercise/update") {
            assertEquals(data.requestId, requestId)
            assertEquals(UpdateExerciseResponse.Result.SUCCESS, result)
            assertNull(errors)
            assertEquals(stubResponseExercise, updateExercise)
        }
    }

    @Test
    fun testPostExerciseDelete() {
        val data = DeleteExerciseRequest(
            requestId = "rID:0004",
            debug = Utils.stubDebugSuccess,
            deleteExerciseId = ExerciseStub.getModelExercise().idExercise.asString()
        )

        testPostRequest<DeleteExerciseResponse>(data, "exercise/delete") {
            assertEquals(data.requestId, requestId)
            assertEquals(DeleteExerciseResponse.Result.SUCCESS, result)
            assertNull(errors)
            assertEquals(stubResponseExercise, deleteExercise)
        }
    }

    @Test
    fun testPostExerciseSearch() {
        val data = SearchExerciseRequest(
            requestId = "rID:0005",
            debug = Utils.stubDebugSuccess,
            search = "Приседания"
        )

        testPostRequest<SearchExerciseResponse>(data, "exercise/search") {
            assertEquals(data.requestId, requestId)
            assertEquals(SearchExerciseResponse.Result.SUCCESS, result)
            assertNull(errors)
            assertNotNull(foundExercises)
            assertEquals(2, foundExercises?.size)
        }
    }
}