package ru.otus.otuskotlin.workout.controllers

import ru.otus.otuskotlin.workout.be.service.openapi.exceptions.WorkoutService
import io.ktor.application.*
import ru.otus.otuskotlin.workout.helpers.handleRoute
import ru.otus.otuskotlin.workout.logging.mpLogger
import ru.otus.otuskotlin.workout.openapi.models.*

suspend fun ApplicationCall.initWorkout(workoutService: WorkoutService) {

    handleRoute<InitWorkoutRequest, InitWorkoutResponse>(
        "init-workout",
        mpLogger(this::class.java)
    ) { request ->
        workoutService.initWorkout(this, request)
    }
}

suspend fun ApplicationCall.createWorkout(workoutService: WorkoutService) {

    handleRoute<CreateWorkoutRequest, CreateWorkoutResponse>(
        "create-workout",
        mpLogger(this::class.java)
    ) { request ->
        workoutService.createWorkout(this, request)
    }
}

suspend fun ApplicationCall.readWorkout(workoutService: WorkoutService) {

    handleRoute<ReadWorkoutRequest, ReadWorkoutResponse>(
        "read-workout",
        mpLogger(this::class.java)
    ) { request ->
        workoutService.readWorkout(this, request)
    }
}

suspend fun ApplicationCall.updateWorkout(workoutService: WorkoutService) {

    handleRoute<UpdateWorkoutRequest, UpdateWorkoutResponse>(
        "update-workout",
        mpLogger(this::class.java)
    ) { request ->
        workoutService.updateWorkout(this, request)
    }
}

suspend fun ApplicationCall.deleteWorkout(workoutService: WorkoutService) {

    handleRoute<DeleteWorkoutRequest, DeleteWorkoutResponse>(
        "delete-workout",
        mpLogger(this::class.java)
    ) { request ->
        workoutService.deleteWorkout(this, request)
    }
}

suspend fun ApplicationCall.searchWorkout(workoutService: WorkoutService) {

    handleRoute<SearchWorkoutRequest, SearchWorkoutResponse>(
        "search-workout",
        mpLogger(this::class.java)
    ) { request ->
        workoutService.searchWorkout(this, request)
    }
}

suspend fun ApplicationCall.chainOfExercises(workoutService: WorkoutService) {

    handleRoute<ChainOfExercisesRequest, ChainOfExercisesResponse>(
        "chain_of_exercises-workout",
        mpLogger(this::class.java)
    ) { request ->
        workoutService.chainOfExercises(this, request)
    }
}