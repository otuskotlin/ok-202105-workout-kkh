package ru.otus.otuskotlin.workout.controllers

import ExerciseService
import io.ktor.application.*
import io.ktor.request.*
import io.ktor.response.*
import ru.otus.otuskotlin.workout.helpers.handleRoute
import ru.otus.otuskotlin.workout.openapi.models.*
import ru.workout.otuskotlin.workout.backend.common.context.BeContext
import java.time.Instant

suspend fun ApplicationCall.initExercise(exerciseService: ExerciseService) {

    handleRoute<InitExerciseRequest, InitExerciseResponse> { request ->
        this.operation = BeContext.MpOperations.INIT
        exerciseService.initExercise(this, request)
    }

//    val initExerciseRequest = receive<InitExerciseRequest>()
//    val context = BeContext(
//        startTime = Instant.now(),
//        operation = BeContext.MpOperations.INIT
//    )
//
//    val result = try {
//        exerciseService.initExercise(context, initExerciseRequest)
//    } catch (e: Exception) {
//        exerciseService.errorExercise(context, e) as InitExerciseResponse
//    }
//    respond(result)

}


suspend fun ApplicationCall.createExercise(exerciseService: ExerciseService) {

    handleRoute<CreateExerciseRequest, CreateExerciseResponse> { request ->
        this.operation = BeContext.MpOperations.CREATE
        exerciseService.createExercise(this, request)
    }

//    val createExerciseRequest = receive<CreateExerciseRequest>()
//    val context = BeContext(
//        startTime = Instant.now(),
//        operation = BeContext.MpOperations.CREATE
//    )
//
//    val result = try {
//        exerciseService.createExercise(context, createExerciseRequest)
//    } catch (e: Throwable) {
//        exerciseService.errorExercise(context, e) as CreateExerciseResponse
//    }
//    respond(result)
}

suspend fun ApplicationCall.readExercise(exerciseService: ExerciseService) {

    handleRoute<ReadExerciseRequest, ReadExerciseResponse> { request ->
        this.operation = BeContext.MpOperations.READ
        exerciseService.readExercise(this, request)
    }

//    val readExerciseRequest = receive<ReadExerciseRequest>()
//    val context = BeContext(
//        startTime = Instant.now(),
//        operation = BeContext.MpOperations.READ
//    )
//    val result = try {
//        exerciseService.readExercise(context, readExerciseRequest)
//    } catch (e: Throwable) {
//        exerciseService.errorExercise(context, e) as ReadExerciseResponse
//    }
//    respond(result)
}

suspend fun ApplicationCall.updateExercise(exerciseService: ExerciseService) {

    handleRoute<UpdateExerciseRequest, UpdateExerciseResponse> { request ->
        this.operation = BeContext.MpOperations.UPDATE
        exerciseService.updateExercise(this, request)
    }

//    val updateExerciseRequest = receive<UpdateExerciseRequest>()
//    val context = BeContext(
//        startTime = Instant.now(),
//        operation = BeContext.MpOperations.UPDATE
//    )
//    val result = try {
//        exerciseService.updateExercise(context, updateExerciseRequest)
//    } catch (e: Throwable) {
//        exerciseService.errorExercise(context, e) as UpdateExerciseResponse
//    }
//    respond(result)
}

suspend fun ApplicationCall.deleteExercise(exerciseService: ExerciseService) {

    handleRoute<DeleteExerciseRequest, DeleteExerciseResponse> { request ->
        this.operation = BeContext.MpOperations.DELETE
        exerciseService.deleteExercise(this, request)
    }

//    val deleteExerciseRequest = receive<DeleteExerciseRequest>()
//    val context = BeContext(
//        startTime = Instant.now(),
//        operation = BeContext.MpOperations.DELETE
//    )
//    val result = try {
//        exerciseService.deleteExercise(context, deleteExerciseRequest)
//    } catch (e: Throwable) {
//        exerciseService.errorExercise(context, e) as DeleteExerciseResponse
//    }
//    respond(result)
}

suspend fun ApplicationCall.searchExercise(exerciseService: ExerciseService) {

    handleRoute<SearchExerciseRequest, SearchExerciseResponse> { request ->
        this.operation = BeContext.MpOperations.SEARCH
        exerciseService.searchExercise(this, request)
    }

//    val searchExerciseRequest = receive<SearchExerciseRequest>()
//    val context = BeContext(
//        startTime = Instant.now(),
//        operation = BeContext.MpOperations.SEARCH
//    )
//    val result = try {
//        exerciseService.searchExercise(context, searchExerciseRequest)
//    } catch (e: Throwable) {
//        exerciseService.errorExercise(context, e) as SearchExerciseResponse
//    }
//    respond(result)
}