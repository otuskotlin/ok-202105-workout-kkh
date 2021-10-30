package ru.otus.otuskotlin.workout.controllers

import io.ktor.application.*
import io.ktor.http.*
import io.ktor.server.testing.*
import ru.otus.otuskotlin.workout.module
import ru.otus.otuskotlin.workout.Utils
import ru.otus.otuskotlin.workout.auth.testUserToken
import ru.otus.otuskotlin.workout.configs.AppKtorConfig
import ru.otus.otuskotlin.workout.configs.KtorAuthConfig
import ru.otus.otuskotlin.workout.openapi.models.BaseMessage
import kotlin.test.assertEquals

abstract class RouterTest {
    protected inline fun <reified T> testPostRequest(
        body: BaseMessage? = null,
        uri: String,
        config: AppKtorConfig = AppKtorConfig(),
        result: HttpStatusCode = HttpStatusCode.OK,
        crossinline block: T.() -> Unit = {}
    ) {
        withTestApplication({ module(config = config) }) {
            handleRequest(HttpMethod.Post, uri) {
                addHeader(HttpHeaders.ContentType, ContentType.Application.Json.withCharset(Charsets.UTF_8).toString())
                addHeader(HttpHeaders.Authorization, "Bearer ${KtorAuthConfig.testUserToken()}")
                setBody(Utils.mapper.writeValueAsString(body))
            }.apply {
                println("content: ${response.content}")
                assertEquals(result, response.status())
                if (result == HttpStatusCode.OK) {
                    assertEquals(ContentType.Application.Json.withCharset(Charsets.UTF_8), response.contentType())
                    Utils.mapper.readValue(response.content, T::class.java).block()
                }
            }
        }
    }
}