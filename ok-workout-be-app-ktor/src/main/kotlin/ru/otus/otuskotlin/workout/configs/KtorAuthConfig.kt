package ru.otus.otuskotlin.workout.configs

import io.ktor.application.*

data class KtorAuthConfig(
    val secret: String,
    val issuer: String,
    val audience: String,
    val realm: String
) {
    constructor(environment: ApplicationEnvironment) : this(
        secret = environment.config.property("jwt.secret").getString(),
        issuer = environment.config.property("jwt.secret").getString(),
        audience = environment.config.property("jwt.secret").getString(),
        realm = environment.config.property("jwt.secret").getString()
    )

    companion object {
        const val GROUPS_CLAIM = "groups"
        const val F_NAME_CLAIM = "fname"
        const val M_NAME_CLAIM = "mname"
        const val L_NAME_CLAIM = "lname"

        val TEST = KtorAuthConfig(
            secret = "secret",
            issuer = "issuer",
            audience = "audience",
            realm = "realm"
        )
    }
}