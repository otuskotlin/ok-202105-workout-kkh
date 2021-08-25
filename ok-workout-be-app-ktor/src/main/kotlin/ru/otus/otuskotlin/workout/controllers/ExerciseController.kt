package ru.otus.otuskotlin.workout.controllers

import ExerciseService
import io.ktor.application.*
import io.ktor.request.*
import io.ktor.response.*
import ru.otus.otuskotlin.workout.openapi.models.*
import ru.workout.otuskotlin.workout.backend.common.context.BeContext
import java.time.Instant

suspend fun ApplicationCall.initExercise(exerciseService: ExerciseService) {
    val initExerciseRequest = receive<InitExerciseRequest>()
    val context = BeContext(
        startTime = Instant.now()
    )

    val result = try {
        exerciseService.initExercise(context, initExerciseRequest)
    } catch (e: Exception) {
        exerciseService.errorExercise(context, e) as InitExerciseResponse
    }
    respond(result)
}

suspend fun ApplicationCall.createExercise(exerciseService: ExerciseService) {
    val createExerciseRequest = receive<CreateExerciseRequest>()
    val context = BeContext(
        startTime = Instant.now()
    )

    val result = try {
        exerciseService.createExercise(context, createExerciseRequest)
    } catch (e: Throwable) {
        exerciseService.errorExercise(context, e) as CreateExerciseResponse
    }
    respond(result)
}

suspend fun ApplicationCall.readExercise(exerciseService: ExerciseService) {
    val readExerciseRequest = receive<ReadExerciseRequest>()
    val context = BeContext(
        startTime = Instant.now()
    )
    val result = try {
        exerciseService.readExercise(context, readExerciseRequest)
    } catch (e: Throwable) {
        exerciseService.errorExercise(context, e) as ReadExerciseResponse
    }
    respond(result)
}

suspend fun ApplicationCall.updateExercise(exerciseService: ExerciseService) {
    val updateExerciseRequest = receive<UpdateExerciseRequest>()
    val context = BeContext(
        startTime = Instant.now()
    )
    val result = try {
        exerciseService.updateExercise(context, updateExerciseRequest)
    } catch (e: Throwable) {
        exerciseService.errorExercise(context, e) as UpdateExerciseResponse
    }
    respond(result)
}

suspend fun ApplicationCall.deleteExercise(exerciseService: ExerciseService) {
    val deleteExerciseRequest = receive<DeleteExerciseRequest>()
    val context = BeContext(
        startTime = Instant.now()
    )
    val result = try {
        exerciseService.deleteExercise(context, deleteExerciseRequest)
    } catch (e: Throwable) {
        exerciseService.errorExercise(context, e) as DeleteExerciseResponse
    }
    respond(result)
}

suspend fun ApplicationCall.searchExercise(exerciseService: ExerciseService) {
    val searchExerciseRequest = receive<SearchExerciseRequest>()
    val context = BeContext(
        startTime = Instant.now()
    )
    val result = try {
        exerciseService.searchExercise(context, searchExerciseRequest)
    } catch (e: Throwable) {
        exerciseService.errorExercise(context, e) as SearchExerciseResponse
    }
    respond(result)
}