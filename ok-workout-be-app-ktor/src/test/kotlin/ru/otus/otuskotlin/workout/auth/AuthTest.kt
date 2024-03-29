package ru.otus.otuskotlin.workout.auth

import io.ktor.http.*
import ru.otus.otuskotlin.workout.Utils
import ru.otus.otuskotlin.workout.configs.AppKtorConfig
import ru.otus.otuskotlin.workout.configs.KtorAuthConfig
import ru.otus.otuskotlin.workout.controllers.RouterTest
import ru.otus.otuskotlin.workout.openapi.models.ReadExerciseRequest
import ru.otus.otuskotlin.workout.openapi.models.ReadExerciseResponse
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

class AuthTest : RouterTest() {

    private val data = ReadExerciseRequest(readExerciseId = "eID:0001", debug = Utils.stubDebugSuccess)

    @Test
    fun authPositiveTest() {
        testPostRequest<ReadExerciseResponse>(
            body = data,
            uri = "/exercise/read",
            config = AppKtorConfig(
                auth = KtorAuthConfig.TEST.copy()
            )
        ) {
            println(this)
            assertEquals(ReadExerciseResponse.Result.SUCCESS, result)
            assertNull(errors)
            assertEquals(Utils.stubResponseExercise.copy(id = "eID:0001"), readExercise)
        }
    }

    @Test
    fun authWrongIssuerTest() {
        testPostRequest<ReadExerciseResponse>(
            body = data,
            uri = "/exercise/read",
            config = AppKtorConfig(
                auth = KtorAuthConfig.TEST.copy(
                    issuer = "other"
                )
            ),
            result = HttpStatusCode.Unauthorized
        )
    }

    @Test
    fun authWrongSecretTest() {
        testPostRequest<ReadExerciseResponse>(
            body = data,
            uri = "/exercise/read",
            config = AppKtorConfig(
                auth = KtorAuthConfig.TEST.copy(
                    secret = "other"
                )
            ),
            result = HttpStatusCode.Unauthorized
        )
    }

    @Test
    fun authWrongAudienceTest() {
        testPostRequest<ReadExerciseResponse>(
            body = data,
            uri = "/exercise/read",
            config = AppKtorConfig(
                auth = KtorAuthConfig.TEST.copy(
                    audience = "other"
                )
            ),
            result = HttpStatusCode.Unauthorized
        )
    }
}