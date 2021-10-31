package ru.otus.otuskotlin.workout

import io.ktor.http.*
import io.ktor.application.*
import kotlin.test.*
import io.ktor.server.testing.*
import ru.otus.otuskotlin.workout.configs.AppKtorConfig

class ApplicationTest {
    @Test
    fun `root endpoint`() {
        withTestApplication({ module(config = AppKtorConfig()) }) {
            handleRequest(HttpMethod.Get, "/").apply {
                assertEquals(HttpStatusCode.OK, response.status())
                assertEquals("Hello, Ktor!", response.content)
            }
        }
    }
}