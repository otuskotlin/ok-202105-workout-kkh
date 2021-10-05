package ru.otus.otuskotlin.workout.controllers

import ExerciseService
import IHandlerRequests
import WorkoutService
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import io.ktor.http.cio.websocket.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.NonCancellable
import kotlinx.coroutines.channels.ClosedReceiveChannelException
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.otus.otuskotlin.workout.objectMapper
import ru.otus.otuskotlin.workout.openapi.models.*
import ru.workout.otuskotlin.workout.backend.common.context.BeContext
import ru.workout.otuskotlin.workout.backend.common.models.IUserSession
import ru.workout.otuskotlin.workout.backend.mapping.openapi.toUpdateExerciseResponse
import ru.workout.otuskotlin.workout.backend.mapping.openapi.toUpdateWorkoutResponse
import java.time.Instant

// TODO: 05.10.2021 Refactoring

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
    userSessions: MutableSet<KtorUserSessionOfExercise>
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
                    outgoing.send(Frame.Text(objectMapper.safeWriteValueAsString(request)))
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

//    try {
//        for (frame in incoming) {
//            launch {
//                if (frame is Frame.Text) {
//                    val context = BeContext(startTime = Instant.now())
//                    try {
//                        val response = when (val request = objectMapper.readValue<BaseMessage>(frame.readText())) {
//                            is InitExerciseRequest -> exerciseService.initExercise(context, request)
//                            is CreateExerciseRequest -> exerciseService.createExercise(context, request)
//                            is ReadExerciseRequest -> exerciseService.readExercise(context, request)
//                            is UpdateExerciseRequest -> exerciseService.updateExercise(context, request)
//                            is DeleteExerciseRequest -> exerciseService.deleteExercise(context, request)
//                            is SearchExerciseRequest -> exerciseService.searchExercise(context, request)
//                            else -> throw UnsupportedOperationException("Unsupported request type")
//                        }
//                        outgoing.send(Frame.Text(objectMapper.writeValueAsString(response)))
//                    } catch (e: Exception) {
//                        val response = exerciseService.handleError(context, e)
//                        outgoing.send(Frame.Text(objectMapper.writeValueAsString(response)))
//                    }
//                }
//            }
//        }
//    } catch (_: ClosedReceiveChannelException) {
//        val context = BeContext(startTime = Instant.now())
//        try {
//            exerciseService.userDisconnected(context)
//        } catch (t: Throwable) {
//            exerciseService.handleError(context, t)
//        }
//    }
}

suspend fun WebSocketSession.handleSessionOfWorkout(
    objectMapper: ObjectMapper,
    workoutService: WorkoutService,
    userSessions: MutableSet<KtorUserSessionOfWorkout>
) {

    val userSession = KtorUserSessionOfWorkout(this, objectMapper)
    userSessions.add(userSession)

    try {
        run {
            // Обработка события init сессии
            serveRequest(InitExerciseRequest(), userSession, workoutService)?.also {
                outgoing.send(Frame.Text(objectMapper.safeWriteValueAsString(it)))
            }
        }
        for (frame in incoming) {
            if (frame is Frame.Text) {
                val request = objectMapper.readValue<BaseMessage>(frame.readText())
                serveRequest(request, userSession, workoutService)?.also {
                    outgoing.send(Frame.Text(objectMapper.safeWriteValueAsString(request)))
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

//    try {
//        for (frame in incoming) {
//            launch {
//                if (frame is Frame.Text) {
//                    val context = BeContext(startTime = Instant.now())
//                    try {
//                        val response = when (val request = objectMapper.readValue<BaseMessage>(frame.readText())) {
//                            is InitWorkoutRequest -> workoutService.initWorkout(context, request)
//                            is CreateWorkoutRequest -> workoutService.createWorkout(context, request)
//                            is ReadWorkoutRequest -> workoutService.readWorkout(context, request)
//                            is UpdateWorkoutRequest -> workoutService.updateWorkout(context, request)
//                            is DeleteWorkoutRequest -> workoutService.deleteWorkout(context, request)
//                            is SearchWorkoutRequest -> workoutService.searchWorkout(context, request)
//                            is ChainOfExercisesRequest -> workoutService.chainOfExercises(context, request)
//                            else -> throw UnsupportedOperationException("Unsupported request type")
//                        }
//                        outgoing.send(Frame.Text(objectMapper.writeValueAsString(response)))
//                    } catch (e: Exception) {
//                        val response = workoutService.handleError(context, e)
//                        outgoing.send(Frame.Text(objectMapper.writeValueAsString(response)))
//                    }
//                }
//            }
//        }
//
//    } catch (_: ClosedReceiveChannelException) {
//        val context = BeContext(startTime = Instant.now())
//        try {
//            workoutService.userDisconnected(context)
//        } catch (t: Throwable) {
//            workoutService.handleError(context, t)
//        }
//    }
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