package ru.otus.otuskotlin.workout.auth

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import ru.otus.otuskotlin.workout.configs.KtorAuthConfig
import java.util.*

fun KtorAuthConfig.Companion.testUserToken(): String = testToken("TEST", "USER")
fun KtorAuthConfig.Companion.testAdminToken(): String = testToken("TEST", "USER", "ADMIN")

private fun testToken(vararg groups: String) = JWT.create()
    .withExpiresAt(
        GregorianCalendar().apply {
            set(2023, 0, 1, 0, 0, 0)
            timeZone = TimeZone.getTimeZone("UTC")
        }.time
    )
    .withAudience(KtorAuthConfig.TEST.audience)
    .withIssuer(KtorAuthConfig.TEST.issuer)
    .withClaim(KtorAuthConfig.F_NAME_CLAIM, "Ivan")
    .withClaim(KtorAuthConfig.M_NAME_CLAIM, "S.")
    .withClaim(KtorAuthConfig.L_NAME_CLAIM, "Ivanov")
    .withArrayClaim(KtorAuthConfig.GROUPS_CLAIM, groups)
    .sign(Algorithm.HMAC256(KtorAuthConfig.TEST.secret))
    .apply { println("Test JWT token: $this") }