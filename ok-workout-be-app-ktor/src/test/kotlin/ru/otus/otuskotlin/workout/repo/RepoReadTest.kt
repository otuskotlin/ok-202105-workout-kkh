package ru.otus.otuskotlin.workout.repo

import com.fasterxml.jackson.databind.ObjectMapper
import io.ktor.http.*
import io.ktor.server.testing.*
import org.junit.Test
import ru.otus.otuskotlin.workout.AppKtorConfig
import ru.otus.otuskotlin.workout.Utils
import ru.otus.otuskotlin.workout.be.repo.inmemory.models.RepoExerciseInMemory
import ru.otus.otuskotlin.workout.module
import ru.otus.otuskotlin.workout.openapi.models.BaseDebugRequest
import ru.otus.otuskotlin.workout.openapi.models.ReadExerciseRequest
import ru.otus.otuskotlin.workout.openapi.models.ReadExerciseResponse
import kotlin.test.assertEquals

class RepoReadTest {

    @Test
    fun readFromDb() {
        val exercise = ExerciseStub.getModelExercise()

        withTestApplication({
            val config = AppKtorConfig(
                exerciseRepoTest = RepoExerciseInMemory(
                    initObjects = listOf(exercise)
                )
            )
            module(testing = true, config = config)
        }) {
            handleRequest(HttpMethod.Post, "exercise/read") {
                addHeader(HttpHeaders.ContentType, ContentType.Application.Json.withCharset(Charsets.UTF_8).toString())
                val request = ReadExerciseRequest(
                    requestId = "rId:00022",
                    readExerciseId = exercise.idExercise.asString(),
                    debug = BaseDebugRequest(mode = BaseDebugRequest.Mode.TEST)
                )
                val json = ObjectMapper().writeValueAsString(request)
                setBody(json)
            }.apply {
                println(response.content)
                assertEquals(HttpStatusCode.OK, response.status())
                assertEquals(ContentType.Application.Json.withCharset(Charsets.UTF_8), response.contentType())
                val result = Utils.mapper.readValue(response.content, ReadExerciseResponse::class.java)
                assertEquals(ReadExerciseResponse.Result.SUCCESS, result.result)
                assertEquals(exercise.idExercise.asString(), result.readExercise?.id)
                assertEquals(exercise.title, result.readExercise?.title)
            }
        }
    }
}