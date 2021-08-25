package ru.otus.otuskotlin.workout.controllers

import org.junit.Test
import ru.otus.otuskotlin.workout.Utils
import ru.otus.otuskotlin.workout.openapi.models.*
import kotlin.test.assertEquals
import kotlin.test.assertNull

class ExerciseRouterTest : RouterTest() {
    @Test
    fun testPostExerciseCreate() {
        val data = CreateExerciseRequest(debug = Utils.stubDebug)

        testPostRequest<CreateExerciseResponse>(data, "exercise/create") {
            assertEquals(CreateExerciseResponse.Result.SUCCESS, result)
            assertNull(errors)
            assertEquals(Utils.stubResponseExercise, createdExercise)
        }
    }

    @Test
    fun testPostExerciseRead() {
        val data = ReadExerciseRequest(
            debug = Utils.stubDebug
        )

        testPostRequest<ReadExerciseResponse>(data, "exercise/read") {
            assertEquals(ReadExerciseResponse.Result.SUCCESS, result)
            assertNull(errors)
            assertEquals(Utils.stubResponseExercise, readExercise)
        }
    }

    @Test
    fun testPostExerciseUpdate() {
        val data = UpdateExerciseRequest(
            debug = Utils.stubDebug
        )

        testPostRequest<UpdateExerciseResponse>(data, "exercise/update") {
            assertEquals(UpdateExerciseResponse.Result.SUCCESS, result)
            assertNull(errors)
//            assertEquals(Utils.stubResponseExercise, updateExercise)
            assertEquals(Utils.stubResponseExercise.copy(permissions = null), updateExercise)
        }
    }
}