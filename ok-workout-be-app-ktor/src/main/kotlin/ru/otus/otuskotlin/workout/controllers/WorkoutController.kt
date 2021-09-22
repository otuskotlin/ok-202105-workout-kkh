package ru.otus.otuskotlin.workout.controllers

import WorkoutService
import io.ktor.application.*
import ru.otus.otuskotlin.workout.helpers.handleRoute
import ru.otus.otuskotlin.workout.openapi.models.*
import ru.workout.otuskotlin.workout.backend.common.context.BeContext

suspend fun ApplicationCall.initWorkout(workoutService: WorkoutService) {

    handleRoute<InitWorkoutRequest, InitWorkoutResponse> { request ->
        this.operation = BeContext.MpOperations.INIT
        workoutService.initWorkout(this, request)
    }
}

suspend fun ApplicationCall.createWorkout(workoutService: WorkoutService) {

    handleRoute<CreateWorkoutRequest, CreateWorkoutResponse> { request ->
        this.operation = BeContext.MpOperations.CREATE
        workoutService.createWorkout(this, request)
    }
}

suspend fun ApplicationCall.readWorkout(workoutService: WorkoutService) {

    handleRoute<ReadWorkoutRequest, ReadWorkoutResponse> { request ->
        this.operation = BeContext.MpOperations.READ
        workoutService.readWorkout(this, request)
    }
}

suspend fun ApplicationCall.updateWorkout(workoutService: WorkoutService) {

    handleRoute<UpdateWorkoutRequest, UpdateWorkoutResponse> { request ->
        this.operation = BeContext.MpOperations.UPDATE
        workoutService.updateWorkout(this, request)
    }
}

suspend fun ApplicationCall.deleteWorkout(workoutService: WorkoutService) {

    handleRoute<DeleteWorkoutRequest, DeleteWorkoutResponse> { request ->
        this.operation = BeContext.MpOperations.DELETE
        workoutService.deleteWorkout(this, request)
    }
}

suspend fun ApplicationCall.searchWorkout(workoutService: WorkoutService) {

    handleRoute<SearchWorkoutRequest, SearchWorkoutResponse> { request ->
        this.operation = BeContext.MpOperations.SEARCH
        workoutService.searchWorkout(this, request)
    }
}

suspend fun ApplicationCall.chainOfExercises(workoutService: WorkoutService) {

    handleRoute<ChainOfExercisesRequest, ChainOfExercisesResponse> { request ->
        this.operation = BeContext.MpOperations.CHAIN_OF_EXERCISES
        workoutService.chainOfExercises(this, request)
    }
}