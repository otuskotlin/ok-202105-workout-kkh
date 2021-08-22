package ru.otus.controllers

import io.ktor.application.*
import io.ktor.request.*
import io.ktor.response.*
import ru.otus.otuskotlin.workout.openapi.models.CreateExerciseRequest
import ru.otus.services.ExerciseService
import ru.workout.otuskotlin.workout.backend.common.context.BeContext
import ru.workout.otuskotlin.workout.backend.mapping.openapi.setQuery
import ru.workout.otuskotlin.workout.backend.mapping.openapi.toCreateExerciseResponse

suspend fun ApplicationCall.createExercise(exerciseService: ExerciseService) {
    println("createExercise")
    val createExerciseRequest = receive<CreateExerciseRequest>()

    respond(
        BeContext().setQuery(createExerciseRequest).let {
            exerciseService.createExercise(it)
        }.toCreateExerciseResponse()
    )
}

suspend fun ApplicationCall.readExercise(exerciseService: ExerciseService) {

}

suspend fun ApplicationCall.updateExercise(exerciseService: ExerciseService) {

}

suspend fun ApplicationCall.deleteExercise(exerciseService: ExerciseService) {

}

suspend fun ApplicationCall.searchExercise(exerciseService: ExerciseService) {

}