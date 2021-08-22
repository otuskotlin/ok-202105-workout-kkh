package ru.otus.controllers

import io.ktor.application.*
import io.ktor.request.*
import io.ktor.response.*
import ru.otus.otuskotlin.workout.openapi.models.*
import ru.otus.services.ExerciseService
import ru.workout.otuskotlin.workout.backend.common.context.BeContext
import ru.workout.otuskotlin.workout.backend.mapping.openapi.setQuery
import ru.workout.otuskotlin.workout.backend.mapping.openapi.toCreateExerciseResponse
import ru.workout.otuskotlin.workout.backend.mapping.openapi.toReadExerciseResponse

suspend fun ApplicationCall.createExercise(exerciseService: ExerciseService) {
    val createExerciseRequest = receive<CreateExerciseRequest>()

    respond(
        BeContext().setQuery(createExerciseRequest).let {
            exerciseService.createExercise(it)
        }.toCreateExerciseResponse()
    )
}

suspend fun ApplicationCall.readExercise(exerciseService: ExerciseService) {
    val readExerciseRequest = receive<ReadExerciseRequest>()
    respond(
        BeContext().setQuery(readExerciseRequest).let {
            exerciseService.readExercise(it)
        }.toReadExerciseResponse()
    )
}

suspend fun ApplicationCall.updateExercise(exerciseService: ExerciseService) {
    val updateExerciseRequest = receive<UpdateExerciseRequest>()
    respond(
        BeContext().setQuery(updateExerciseRequest).let {
            exerciseService.updateExercise(it)
        }
    )
}

suspend fun ApplicationCall.deleteExercise(exerciseService: ExerciseService) {
    val deleteExerciseRequest = receive<DeleteExerciseRequest>()
    respond(
        BeContext().setQuery(deleteExerciseRequest).let {
            exerciseService.deleteExercise(it)
        }
    )
}

suspend fun ApplicationCall.searchExercise(exerciseService: ExerciseService) {
    val searchExerciseRequest = receive<SearchExerciseRequest>()
    respond(
        BeContext().setQuery(searchExerciseRequest).let {
            exerciseService.searchExercise(it)
        }
    )
}