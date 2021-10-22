package ru.otus.otuskotlin.workout.repo

import com.fasterxml.jackson.databind.ObjectMapper
import io.ktor.http.*
import io.ktor.server.testing.*
import org.junit.Test
import ru.otus.otuskotlin.workout.AppKtorConfig
import ru.otus.otuskotlin.workout.Utils
import ru.otus.otuskotlin.workout.backend.common.models.ExerciseIdModel
import ru.otus.otuskotlin.workout.backend.common.repo.common.exercise.IRepoExercise
import ru.otus.otuskotlin.workout.backend.mapping.openapi.toTransport
import ru.otus.otuskotlin.workout.be.repo.inmemory.models.RepoExerciseInMemory
import ru.otus.otuskotlin.workout.module
import ru.otus.otuskotlin.workout.openapi.models.*
import kotlin.test.assertEquals

class RepoReadTest {

    @Test
    fun createToDb() {
        val expected = ExerciseStub.getModelExercise() { idExercise = ExerciseIdModel.NONE }
        withTestApplication({
            val config = AppKtorConfig(
                exerciseRepoTest = RepoExerciseInMemory(
                    initObjects = emptyList()
                )
            )
            module(testing = true, config = config)
        }) {
            handleRequest(HttpMethod.Post, "exercise/create") {
                addHeader(HttpHeaders.ContentType, ContentType.Application.Json.withCharset(Charsets.UTF_8).toString())
                val request = CreateExerciseRequest(
                    requestId = "rId:00021",
                    createExercise = Utils.stubCreatableExercise,
                    debug = BaseDebugRequest(mode = BaseDebugRequest.Mode.TEST)
                )
                val json = ObjectMapper().writeValueAsString(request)
                setBody(json)
            }.apply {
                println(response.content)
                assertEquals(HttpStatusCode.OK, response.status())
                assertEquals(ContentType.Application.Json.withCharset(Charsets.UTF_8), response.contentType())
                val result = Utils.mapper.readValue(response.content, CreateExerciseResponse::class.java)
                expected.idExercise = result.createdExercise?.id?.let { ExerciseIdModel(it) } ?: ExerciseIdModel.NONE
                assertEquals(CreateExerciseResponse.Result.SUCCESS, result.result)
                assertEquals(expected.idExercise.asString(), result.createdExercise?.id)
                assertEquals(expected.title, result.createdExercise?.title)
            }
        }
    }

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