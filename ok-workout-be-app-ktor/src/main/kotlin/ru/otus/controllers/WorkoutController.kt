package ru.otus.controllers

import io.ktor.application.*
import io.ktor.request.*
import io.ktor.response.*
import ru.otus.otuskotlin.workout.openapi.models.*
import ru.otus.services.WorkoutService
import ru.workout.otuskotlin.workout.backend.common.context.BeContext
import ru.workout.otuskotlin.workout.backend.mapping.openapi.*

suspend fun ApplicationCall.createWorkout(workoutService: WorkoutService) {
    val createWorkoutRequest = receive<CreateWorkoutRequest>()

    respond(
        BeContext().setQuery(createWorkoutRequest).let {
            workoutService.createWorkout(it)
        }.toCreateWorkoutResponse()
    )
}

suspend fun ApplicationCall.readWorkout(workoutService: WorkoutService) {
    val readWorkoutRequest = receive<ReadWorkoutRequest>()

    respond(
        BeContext().setQuery(readWorkoutRequest).let {
            workoutService.readWorkout(it)
        }.toReadWorkoutResponse()
    )
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