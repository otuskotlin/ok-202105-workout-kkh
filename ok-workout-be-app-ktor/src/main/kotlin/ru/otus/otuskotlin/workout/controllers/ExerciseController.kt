package ru.otus.otuskotlin.workout.controllers

import ExerciseService
import io.ktor.application.*
import ru.otus.otuskotlin.workout.helpers.handleRoute
import ru.otus.otuskotlin.workout.openapi.models.*

suspend fun ApplicationCall.initExercise(exerciseService: ExerciseService) {

    handleRoute<InitExerciseRequest, InitExerciseResponse> { request ->
        exerciseService.initExercise(this, request)
    }
}

suspend fun ApplicationCall.createExercise(exerciseService: ExerciseService) {

    handleRoute<CreateExerciseRequest, CreateExerciseResponse> { request ->
        exerciseService.createExercise(this, request)
    }
}

suspend fun ApplicationCall.readExercise(exerciseService: ExerciseService) {

    handleRoute<ReadExerciseRequest, ReadExerciseResponse> { request ->
        exerciseService.readExercise(this, request)
    }
}

suspend fun ApplicationCall.updateExercise(exerciseService: ExerciseService) {

    handleRoute<UpdateExerciseRequest, UpdateExerciseResponse> { request ->
        exerciseService.updateExercise(this, request)
    }
}

suspend fun ApplicationCall.deleteExercise(exerciseService: ExerciseService) {

    handleRoute<DeleteExerciseRequest, DeleteExerciseResponse> { request ->
        exerciseService.deleteExercise(this, request)
    }
}

suspend fun ApplicationCall.searchExercise(exerciseService: ExerciseService) {

    handleRoute<SearchExerciseRequest, SearchExerciseResponse> { request ->
        exerciseService.searchExercise(this, request)
    }
}