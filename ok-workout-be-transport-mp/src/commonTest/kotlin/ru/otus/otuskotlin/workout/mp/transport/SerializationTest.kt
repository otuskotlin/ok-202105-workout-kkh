package ru.otus.otuskotlin.workout.mp.transport

import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import ru.otus.otuskotlin.workout.mp.transport.models.CreateExerciseRequest
import kotlin.test.Test
import kotlin.test.assertContains

class SerializationTest {

    @Test
    fun requestSerialTest() {
        val request = CreateExerciseRequest(
            requestId = "111"
        )

        val json = Json {
            prettyPrint = true
        }

        val serialString = json.encodeToString(request)

        println(serialString)
        assertContains(serialString, Regex("requestId\":\\s*\"111"))
    }
}