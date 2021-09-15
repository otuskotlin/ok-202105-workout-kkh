package ru.otus.otuskotlin.workout.helpers

import io.ktor.application.*
import io.ktor.request.*
import io.ktor.response.*
import ru.otus.otuskotlin.workout.openapi.models.BaseMessage
import ru.workout.otuskotlin.workout.backend.common.context.BeContext
import java.time.Instant

suspend inline fun <reified T : BaseMessage, reified U : BaseMessage> ApplicationCall.handleRoute(
    block: BeContext.(T) -> U
) {
    val request = receive<BaseMessage>() as T
    val context = BeContext(
        startTime = Instant.now(),
    )
    try {
        val response = context.block(request)
        respond(response)
    } catch (e: Exception) {
        context.addError(e)
        val response = context.block(request)
        respond(response)
    }
}