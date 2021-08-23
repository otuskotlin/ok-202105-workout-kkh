package ru.otus.controllers

import io.ktor.application.*
import io.ktor.request.*
import io.ktor.response.*
import ru.otus.otuskotlin.workout.openapi.models.CreateWorkoutRequest
import ru.otus.services.WorkoutService
import ru.workout.otuskotlin.workout.backend.common.context.BeContext
import ru.workout.otuskotlin.workout.backend.mapping.openapi.setQuery
import ru.workout.otuskotlin.workout.backend.mapping.openapi.toCreateWorkoutResponse

suspend fun ApplicationCall.createWorkout(workoutService: WorkoutService) {
    val createWorkoutRequest = receive<CreateWorkoutRequest>()

    respond(
        BeContext().setQuery(createWorkoutRequest).let {
            workoutService.createWorkout(it)
        }.toCreateWorkoutResponse()
    )
}