package ru.otus.otuskotlin.workout

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.SerializationFeature
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.auth.jwt.*
import io.ktor.features.*
import io.ktor.jackson.*
import io.ktor.websocket.*
import ru.otus.otuskotlin.workout.plugins.configRouting
import io.ktor.server.netty.EngineMain
import ru.otus.otuskotlin.workout.configs.AppKtorConfig

fun main(args: Array<String>): Unit = EngineMain.main(args)

@Suppress("unused")
@JvmOverloads
fun Application.module(
    testing: Boolean = false,
    config: AppKtorConfig = AppKtorConfig()
) {
    install(Authentication) {
        jwt("auth-jwt") {
            realm = config.auth.realm
            verifier(
                JWT
                    .require(Algorithm.HMAC256(config.auth.secret))
                    .withAudience(config.auth.audience)
                    .withIssuer(config.auth.issuer)
                    .build()
            )
        }
    }

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
