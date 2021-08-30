package ru.otus.otuskotlin.workout.controllers

import WorkoutService
import io.ktor.application.*
import io.ktor.request.*
import io.ktor.response.*
import ru.otus.otuskotlin.workout.openapi.models.*
import ru.workout.otuskotlin.workout.backend.common.context.BeContext
import java.time.Instant

suspend fun ApplicationCall.initWorkout(workoutService: WorkoutService) {
    val initWorkoutRequest = receive<InitWorkoutRequest>()
    val context = BeContext(
        startTime = Instant.now()
    )
    val result = try {
        workoutService.initWorkout(context, initWorkoutRequest)
    } catch (e: Throwable) {
        workoutService.errorWorkout(context, e) as InitWorkoutResponse
    }
    respond(result)
}

suspend fun ApplicationCall.createWorkout(workoutService: WorkoutService) {
    val createWorkoutRequest = receive<CreateWorkoutRequest>()
    val context = BeContext(
        startTime = Instant.now()
    )
    val result = try {
        workoutService.createWorkout(context, createWorkoutRequest)
    } catch (e: Throwable) {
        workoutService.errorWorkout(context, e) as CreateWorkoutResponse
    }
    respond(result)
}

suspend fun ApplicationCall.readWorkout(workoutService: WorkoutService) {
    val readWorkoutRequest = receive<ReadWorkoutRequest>()
    val context = BeContext(
        startTime = Instant.now()
    )
    val result = try {
        workoutService.readWorkout(context, readWorkoutRequest)
    } catch (e: Throwable) {
        workoutService.errorWorkout(context, e) as ReadWorkoutResponse
    }
    respond(result)
}

suspend fun ApplicationCall.updateWorkout(workoutService: WorkoutService) {
    val updateWorkoutRequest = receive<UpdateWorkoutRequest>()
    val context = BeContext(
        startTime = Instant.now()
    )
    val result = try {
        workoutService.updateWorkout(context, updateWorkoutRequest)
    } catch (e: Throwable) {
        workoutService.errorWorkout(context, e) as UpdateWorkoutResponse
    }
    respond(result)
}

suspend fun ApplicationCall.deleteWorkout(workoutService: WorkoutService) {
    val deleteWorkoutRequest = receive<DeleteWorkoutRequest>()
    val context = BeContext(
        startTime = Instant.now()
    )
    val result = try {
        workoutService.deleteWorkout(context, deleteWorkoutRequest)
    } catch (e: Throwable) {
        workoutService.errorWorkout(context, e) as DeleteExerciseResponse
    }
    respond(result)
}

suspend fun ApplicationCall.searchWorkout(workoutService: WorkoutService) {
    val searchWorkoutRequest = receive<SearchWorkoutRequest>()
    val context = BeContext(
        startTime = Instant.now()
    )
    println(context.requestSearchWorkout)
    val result = try {
        workoutService.searchWorkout(context, searchWorkoutRequest)
    } catch (e: Throwable) {
        println("Trouble: $e")
        workoutService.errorWorkout(context, e) as SearchWorkoutResponse
    }
    respond(result)
}

suspend fun ApplicationCall.chainOfExercises(workoutService: WorkoutService) {
    val readWorkoutRequest = receive<ReadWorkoutRequest>()
    val context = BeContext(
        startTime = Instant.now()
    )
    val result = try {
        workoutService.chainOfExercises(context, readWorkoutRequest)
    } catch (e: Exception) {
        println("Ошибка: ${e.message}")
        workoutService.errorWorkout(context, e) as ChainOfExercisesResponse
    }
    respond(result)
}