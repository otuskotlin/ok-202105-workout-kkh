package ru.otus.otuskotlin.workout.controllers

import ExerciseService
import IHandlerRequests
import WorkoutService
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import io.ktor.http.cio.websocket.*
import kotlinx.coroutines.NonCancellable
import kotlinx.coroutines.channels.ClosedReceiveChannelException
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.otus.otuskotlin.workout.objectMapper
import ru.otus.otuskotlin.workout.openapi.models.*
import ru.workout.otuskotlin.workout.backend.common.context.BeContext
import java.time.Instant

suspend fun WebSocketSession.handleSessionOfExercise(
    objectMapper: ObjectMapper,
    exerciseService: ExerciseService
) {
    connectSession(exerciseService)

    try {
        for (frame in incoming) {
            launch {
                if (frame is Frame.Text) {
                    val context = BeContext(startTime = Instant.now())
                    try {
                        val response = when (val request = objectMapper.readValue<BaseMessage>(frame.readText())) {
                            is InitExerciseRequest -> exerciseService.initExercise(context, request)
                            is CreateExerciseRequest -> exerciseService.createExercise(context, request)
                            is ReadExerciseRequest -> exerciseService.readExercise(context, request)
                            is UpdateExerciseRequest -> exerciseService.updateExercise(context, request)
                            is DeleteExerciseRequest -> exerciseService.deleteExercise(context, request)
                            is SearchExerciseRequest -> exerciseService.searchExercise(context, request)
                            else -> throw UnsupportedOperationException("Unsupported request type")
                        }
                        outgoing.send(Frame.Text(objectMapper.writeValueAsString(response)))
                    } catch (e: Exception) {
                        val response = exerciseService.handleError(context, e)
                        outgoing.send(Frame.Text(objectMapper.writeValueAsString(response)))
                    }
                }
            }
        }
    } catch (_: ClosedReceiveChannelException) {
        val context = BeContext(startTime = Instant.now())
        try {
            exerciseService.userDisconnected(context)
        } catch (t: Throwable) {
            exerciseService.handleError(context, t)
        }
    }
}

suspend fun WebSocketSession.handleSessionOfWorkout(
    objectMapper: ObjectMapper,
    workoutService: WorkoutService
) {
    connectSession(workoutService)

    try {
        for (frame in incoming) {
            launch {
                if (frame is Frame.Text) {
                    val context = BeContext(startTime = Instant.now())
                    try {
                        val response = when (val request = objectMapper.readValue<BaseMessage>(frame.readText())) {
                            is InitWorkoutRequest -> workoutService.initWorkout(context, request)
                            is CreateWorkoutRequest -> workoutService.createWorkout(context, request)
                            is ReadWorkoutRequest -> workoutService.readWorkout(context, request)
                            is UpdateWorkoutRequest -> workoutService.updateWorkout(context, request)
                            is DeleteWorkoutRequest -> workoutService.deleteWorkout(context, request)
                            is SearchWorkoutRequest -> workoutService.searchWorkout(context, request)
                            is ChainOfExercisesRequest -> workoutService.chainOfExercises(context, request)
                            else -> throw UnsupportedOperationException("Unsupported request type")
                        }
                        outgoing.send(Frame.Text(objectMapper.writeValueAsString(response)))
                    } catch (e: Exception) {
                        val response = workoutService.handleError(context, e)
                        outgoing.send(Frame.Text(objectMapper.writeValueAsString(response)))
                    }
                }
            }
        }

    } catch (_: ClosedReceiveChannelException) {
        val context = BeContext(startTime = Instant.now())
        try {
            workoutService.userDisconnected(context)
        } catch (t: Throwable) {
            workoutService.handleError(context, t)
        }
    }
}

suspend fun WebSocketSession.connectSession(handleRequest: IHandlerRequests) {
    val context = BeContext(startTime = Instant.now())
    try {
        withContext(NonCancellable) {
            val response = handleRequest.userConnected(context)
            outgoing.send(Frame.Text(objectMapper.writeValueAsString(response)))
        }
    } catch (t: Throwable) {
        withContext(NonCancellable) {
            val response = handleRequest.handleError(context, t)
            outgoing.send(Frame.Text(objectMapper.writeValueAsString(response)))
        }
    }
}