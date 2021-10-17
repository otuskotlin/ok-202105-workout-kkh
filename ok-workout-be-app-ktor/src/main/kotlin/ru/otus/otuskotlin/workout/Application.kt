package ru.otus.otuskotlin.workout

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.SerializationFeature
import io.ktor.application.*
import io.ktor.features.*
import io.ktor.jackson.*
import io.ktor.websocket.*
import ru.otus.otuskotlin.workout.plugins.configRouting
import io.ktor.server.netty.EngineMain

fun main(args: Array<String>): Unit = EngineMain.main(args)

@Suppress("unused")
@JvmOverloads
fun Application.module(
    testing: Boolean = false,
    config: AppKtorConfig = AppKtorConfig()
) {
    install(ContentNegotiation) {
        jackson {
            disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
            enable(SerializationFeature.INDENT_OUTPUT)
            writerWithDefaultPrettyPrinter()
        }
    }
    install(WebSockets)
    configRouting(config.exerciseService, config.objectMapper, config.userSessions)
}
