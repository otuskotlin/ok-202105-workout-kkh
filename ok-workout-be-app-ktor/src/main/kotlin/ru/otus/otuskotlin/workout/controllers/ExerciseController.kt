package ru.otus.otuskotlin.workout.controllers

import ExerciseService
import io.ktor.application.*
import io.ktor.request.*
import io.ktor.response.*
import ru.otus.otuskotlin.workout.openapi.models.*
import ru.workout.otuskotlin.workout.backend.common.context.BeContext
import ru.workout.otuskotlin.workout.backend.mapping.openapi.*
import java.time.Instant

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
    respond(
        BeContext().setQuery(deleteExerciseRequest).let {
            exerciseService.deleteExercise(it)
        }.toDeleteExerciseResponse()
    )
}

suspend fun ApplicationCall.searchExercise(exerciseService: ExerciseService) {
    val searchExerciseRequest = receive<SearchExerciseRequest>()
    respond(
        BeContext().setQuery(searchExerciseRequest).let {
            exerciseService.searchExercise(it)
        }.toSearchExerciseResponse()
    )
}