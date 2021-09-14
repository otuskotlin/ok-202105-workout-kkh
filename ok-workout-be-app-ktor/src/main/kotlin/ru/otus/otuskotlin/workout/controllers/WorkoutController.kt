package ru.otus.otuskotlin.workout.controllers

import WorkoutService
import io.ktor.application.*
import io.ktor.request.*
import io.ktor.response.*
import ru.otus.otuskotlin.workout.helpers.handleRoute
import ru.otus.otuskotlin.workout.openapi.models.*
import ru.workout.otuskotlin.workout.backend.common.context.BeContext
import java.time.Instant

suspend fun ApplicationCall.initWorkout(workoutService: WorkoutService) {

    handleRoute<InitWorkoutRequest, InitWorkoutResponse> { request ->
        this.operation = BeContext.MpOperations.INIT
        workoutService.initWorkout(this, request)
    }

//    val initWorkoutRequest = receive<InitWorkoutRequest>()
//    val context = BeContext(
//        startTime = Instant.now(),
//        operation = BeContext.MpOperations.INIT
//    )
//    val result = try {
//        workoutService.initWorkout(context, initWorkoutRequest)
//    } catch (e: Throwable) {
//        workoutService.errorWorkout(context, e) as InitWorkoutResponse
//    }
//    respond(result)
}

suspend fun ApplicationCall.createWorkout(workoutService: WorkoutService) {

    handleRoute<CreateWorkoutRequest, CreateWorkoutResponse> { request ->
        this.operation = BeContext.MpOperations.CREATE
        workoutService.createWorkout(this, request)
    }

//    val createWorkoutRequest = receive<CreateWorkoutRequest>()
//    val context = BeContext(
//        startTime = Instant.now(),
//        operation = BeContext.MpOperations.CREATE
//    )
//    val result = try {
//        workoutService.createWorkout(context, createWorkoutRequest)
//    } catch (e: Throwable) {
//        workoutService.errorWorkout(context, e) as CreateWorkoutResponse
//    }
//    respond(result)
}

suspend fun ApplicationCall.readWorkout(workoutService: WorkoutService) {

    handleRoute<ReadWorkoutRequest, ReadWorkoutResponse> { request ->
        this.operation = BeContext.MpOperations.READ
        workoutService.readWorkout(this, request)
    }

//    val readWorkoutRequest = receive<ReadWorkoutRequest>()
//    val context = BeContext(
//        startTime = Instant.now(),
//        operation = BeContext.MpOperations.READ
//    )
//    val result = try {
//        workoutService.readWorkout(context, readWorkoutRequest)
//    } catch (e: Throwable) {
//        workoutService.errorWorkout(context, e) as ReadWorkoutResponse
//    }
//    respond(result)
}

suspend fun ApplicationCall.updateWorkout(workoutService: WorkoutService) {

    handleRoute<UpdateWorkoutRequest, UpdateWorkoutResponse> { request ->
        this.operation = BeContext.MpOperations.UPDATE
        workoutService.updateWorkout(this, request)
    }

//    val updateWorkoutRequest = receive<UpdateWorkoutRequest>()
//    val context = BeContext(
//        startTime = Instant.now(),
//        operation = BeContext.MpOperations.UPDATE
//    )
//    val result = try {
//        workoutService.updateWorkout(context, updateWorkoutRequest)
//    } catch (e: Throwable) {
//        workoutService.errorWorkout(context, e) as UpdateWorkoutResponse
//    }
//    respond(result)
}

suspend fun ApplicationCall.deleteWorkout(workoutService: WorkoutService) {

    handleRoute<DeleteWorkoutRequest, DeleteWorkoutResponse> { request ->
        this.operation = BeContext.MpOperations.DELETE
        workoutService.deleteWorkout(this, request)
    }

//    val deleteWorkoutRequest = receive<DeleteWorkoutRequest>()
//    val context = BeContext(
//        startTime = Instant.now(),
//        operation = BeContext.MpOperations.DELETE
//    )
//    val result = try {
//        workoutService.deleteWorkout(context, deleteWorkoutRequest)
//    } catch (e: Throwable) {
//        workoutService.errorWorkout(context, e) as DeleteExerciseResponse
//    }
//    respond(result)
}

suspend fun ApplicationCall.searchWorkout(workoutService: WorkoutService) {

    handleRoute<SearchWorkoutRequest, SearchWorkoutResponse> { request ->
        this.operation = BeContext.MpOperations.SEARCH
        workoutService.searchWorkout(this, request)
    }

//    val searchWorkoutRequest = receive<SearchWorkoutRequest>()
//    val context = BeContext(
//        startTime = Instant.now(),
//        operation = BeContext.MpOperations.SEARCH
//    )
//    val result = try {
//        workoutService.searchWorkout(context, searchWorkoutRequest)
//    } catch (e: Throwable) {
//        workoutService.errorWorkout(context, e) as SearchWorkoutResponse
//    }
//    respond(result)
}

suspend fun ApplicationCall.chainOfExercises(workoutService: WorkoutService) {

    handleRoute<ReadWorkoutRequest, ChainOfExercisesResponse> { request ->
        this.operation = BeContext.MpOperations.CHAIN_OF_EXERCISES
        workoutService.chainOfExercises(this, request)
    }

//    val readWorkoutRequest = receive<ReadWorkoutRequest>()
//    val context = BeContext(
//        startTime = Instant.now(),
//        operation = BeContext.MpOperations.CHAIN_OF_EXERCISES
//    )
//    val result = try {
//        workoutService.chainOfExercises(context, readWorkoutRequest)
//    } catch (e: Exception) {
//        workoutService.errorWorkout(context, e) as ChainOfExercisesResponse
//    }
//    respond(result)
}