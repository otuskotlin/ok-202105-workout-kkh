package ru.otus.otuskotlin.workout.controllers

import io.ktor.application.*
import io.ktor.http.*
import io.ktor.server.testing.*
import ru.otus.module
import ru.otus.otuskotlin.workout.Utils
import ru.otus.otuskotlin.workout.openapi.models.BaseMessage
import kotlin.test.assertEquals

abstract class RouterTest {
    protected inline fun <reified T> testPostRequest(
        body: BaseMessage? = null,
        uri: String,
        crossinline block: T.() -> Unit
    ) {
        withTestApplication(Application::module) {
            handleRequest(HttpMethod.Post, uri) {
                addHeader(HttpHeaders.ContentType, ContentType.Application.Json.withCharset(Charsets.UTF_8).toString())
                setBody(Utils.mapper.writeValueAsString(body))
            }.apply {
                println(response.content)
                assertEquals(HttpStatusCode.OK, response.status())
                assertEquals(ContentType.Application.Json.withCharset(Charsets.UTF_8), response.contentType())
                Utils.mapper.readValue(response.content, T::class.java).block()
            }
        }
    }
}