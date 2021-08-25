package ru.otus.otuskotlin.workout.controllers

import WorkoutService
import io.ktor.application.*
import io.ktor.request.*
import io.ktor.response.*
import ru.otus.otuskotlin.workout.openapi.models.*
import ru.workout.otuskotlin.workout.backend.common.context.BeContext
import ru.workout.otuskotlin.workout.backend.mapping.openapi.*
import java.time.Instant

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

    respond(
        BeContext().setQuery(updateWorkoutRequest).let {
            workoutService.updateWorkout(it)
        }.toUpdateWorkoutResponse()
    )
}

suspend fun ApplicationCall.deleteWorkout(workoutService: WorkoutService) {
    val deleteWorkoutRequest = receive<DeleteWorkoutRequest>()

    respond(
        BeContext().setQuery(deleteWorkoutRequest).let {
            workoutService.deleteWorkout(it)
        }.toDeleteWorkoutResponse()
    )
}

suspend fun ApplicationCall.searchWorkout(workoutService: WorkoutService) {
    val searchWorkoutRequest = receive<SearchWorkoutRequest>()

    respond(
        BeContext().setQuery(searchWorkoutRequest).let {
            workoutService.searchWorkout(it)
        }.toSearchWorkoutResponse()
    )
}

suspend fun ApplicationCall.chainOfExercises(workoutService: WorkoutService) {
    val readWorkoutRequest = receive<ReadWorkoutRequest>()

    respond(
        BeContext().setQuery(readWorkoutRequest).let {
            workoutService.chainOfExercises(it)
        }.toChainOfExercises()
    )
}