package ru.otus.otuskotlin.workout.controllers

import ru.otus.otuskotlin.workout.be.service.openapi.exceptions.ExerciseService
import ru.otus.otuskotlin.workout.be.service.openapi.exceptions.IHandlerRequests
import ru.otus.otuskotlin.workout.be.service.openapi.exceptions.WorkoutService
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import io.ktor.http.cio.websocket.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.ClosedReceiveChannelException
import kotlinx.coroutines.withContext
import ru.otus.otuskotlin.workout.openapi.models.*
import ru.otus.otuskotlin.workout.backend.common.context.BeContext
import ru.otus.otuskotlin.workout.backend.common.models.IUserSession
import ru.otus.otuskotlin.workout.backend.mapping.openapi.toUpdateExerciseResponse
import ru.otus.otuskotlin.workout.backend.mapping.openapi.toUpdateWorkoutResponse
import java.time.Instant

// TODO: 05.10.2021 Need refactoring

// Это расширение делает вызов потенциально блокирующего метода writeValueAsString безопасным для корутин
suspend fun ObjectMapper.safeWriteValueAsString(value: Any?): String =
    withContext(Dispatchers.IO) { writeValueAsString(value) }

open class KtorUserSession(override val fwSession: WebSocketSession) : IUserSession<WebSocketSession> {
    override suspend fun notifyAdChanged(context: BeContext) {
    }
}

class KtorUserSessionOfExercise(
    override val fwSession: WebSocketSession,
    private val objectMapper: ObjectMapper

) : IUserSession<WebSocketSession>, KtorUserSession(fwSession) {
    override suspend fun notifyAdChanged(context: BeContext) {
        val event = context.toUpdateExerciseResponse()
        fwSession.send(Frame.Text(objectMapper.safeWriteValueAsString(event)))
    }
}

class KtorUserSessionOfWorkout(
    override val fwSession: WebSocketSession,
    private val objectMapper: ObjectMapper

) : IUserSession<WebSocketSession>, KtorUserSession(fwSession) {
    override suspend fun notifyAdChanged(context: BeContext) {
        val event = context.toUpdateWorkoutResponse()
        fwSession.send(Frame.Text(objectMapper.safeWriteValueAsString(event)))
    }
}

suspend fun WebSocketSession.handleSessionOfExercise(
    objectMapper: ObjectMapper,
    exerciseService: ExerciseService,
    userSessions: MutableSet<KtorUserSession>
) {

    val userSession = KtorUserSessionOfExercise(this, objectMapper)
    userSessions.add(userSession)

    try {
        run {
            // Обработка события init сессии
            serveRequest(InitExerciseRequest(), userSession, exerciseService)?.also {
                outgoing.send(Frame.Text(objectMapper.safeWriteValueAsString(it)))
            }
        }
        for (frame in incoming) {
            if (frame is Frame.Text) {
                val request = objectMapper.readValue<BaseMessage>(frame.readText())
                serveRequest(request, userSession, exerciseService)?.also {
                    outgoing.send(Frame.Text(objectMapper.safeWriteValueAsString(it)))
                }
            }
        }
    } catch (_: ClosedReceiveChannelException) {
        // Веб-сокет закрыт по инициативе клиента
    } finally {
        userSessions.remove(userSession)
    }

    // Обработка события finished сессии
    serveRequest(null, userSession, exerciseService)
}

suspend fun WebSocketSession.handleSessionOfWorkout(
    objectMapper: ObjectMapper,
    workoutService: WorkoutService,
    userSessions: MutableSet<KtorUserSession>
) {

    val userSession = KtorUserSessionOfWorkout(this, objectMapper)
    userSessions.add(userSession)

    try {
        run {
            // Обработка события init сессии
            serveRequest(InitWorkoutRequest(), userSession, workoutService)?.also {
                outgoing.send(Frame.Text(objectMapper.safeWriteValueAsString(it)))
            }
        }
        for (frame in incoming) {
            if (frame is Frame.Text) {
                val request = objectMapper.readValue<BaseMessage>(frame.readText())
                serveRequest(request, userSession, workoutService)?.also {
                    outgoing.send(Frame.Text(objectMapper.safeWriteValueAsString(it)))
                }
            }
        }
    } catch (_: ClosedReceiveChannelException) {
        // Веб-сокет закрыт по инициативе клиента
    } finally {
        userSessions.remove(userSession)
    }

    // Обработка события finished сессии
    serveRequest(null, userSession, workoutService)
}

suspend fun serveRequest(request: BaseMessage?, userSession: KtorUserSession, service: IHandlerRequests): BaseMessage? {
    val context = BeContext(startTime = Instant.now(), userSession = userSession)
    return try {
        if (request != null) {
            service.handleRequest(context, request)
        } else {
            service.finish(context)
            null
        }
    } catch (e: Exception) {
        service.handleError(context, e)
    }
}