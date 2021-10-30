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
import ru.otus.otuskotlin.workout.configs.KtorAuthConfig.Companion.GROUPS_CLAIM

fun main(args: Array<String>): Unit = EngineMain.main(args)

@Suppress("unused")
@JvmOverloads
fun Application.module(
    testing: Boolean = false,
    config: AppKtorConfig = AppKtorConfig(environment)
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
            validate { jwtCredential ->
                when {
                    !jwtCredential.payload.audience.contains(config.auth.audience) -> {
                        log.error("Unsupported audience in JWT token ${jwtCredential.payload.audience}. Must be ${config.auth.audience}")
                        null
                    }
                    jwtCredential.payload.issuer != config.auth.issuer -> {
                        log.error("Wrong issuer in JWT token ${jwtCredential.payload.issuer}. Must be ${config.auth.issuer}")
                        null
                    }
                    jwtCredential.payload.getClaim(GROUPS_CLAIM).asList(String::class.java).isNullOrEmpty() -> {
                        log.error("Groups claim must not be empty in JWT token")
                        null
                    }
                    else -> JWTPrincipal(jwtCredential.payload)
                }
            }
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
