package ru.otus.otuskotlin.workout.controllers

import ExerciseService
import io.ktor.application.*
import ru.otus.otuskotlin.workout.helpers.handleRoute
import ru.otus.otuskotlin.workout.openapi.models.*
import ru.workout.otuskotlin.workout.backend.common.context.BeContext

suspend fun ApplicationCall.initExercise(exerciseService: ExerciseService) {

    handleRoute<InitExerciseRequest, InitExerciseResponse> { request ->
        this.operation = BeContext.MpOperations.INIT
        exerciseService.initExercise(this, request)
    }
}

suspend fun ApplicationCall.createExercise(exerciseService: ExerciseService) {

    handleRoute<CreateExerciseRequest, CreateExerciseResponse> { request ->
        this.operation = BeContext.MpOperations.CREATE
        exerciseService.createExercise(this, request)
    }
}

suspend fun ApplicationCall.readExercise(exerciseService: ExerciseService) {

    handleRoute<ReadExerciseRequest, ReadExerciseResponse> { request ->
        this.operation = BeContext.MpOperations.READ
        exerciseService.readExercise(this, request)
    }
}

suspend fun ApplicationCall.updateExercise(exerciseService: ExerciseService) {

    handleRoute<UpdateExerciseRequest, UpdateExerciseResponse> { request ->
        this.operation = BeContext.MpOperations.UPDATE
        exerciseService.updateExercise(this, request)
    }
}

suspend fun ApplicationCall.deleteExercise(exerciseService: ExerciseService) {

    handleRoute<DeleteExerciseRequest, DeleteExerciseResponse> { request ->
        this.operation = BeContext.MpOperations.DELETE
        exerciseService.deleteExercise(this, request)
    }
}

suspend fun ApplicationCall.searchExercise(exerciseService: ExerciseService) {

    handleRoute<SearchExerciseRequest, SearchExerciseResponse> { request ->
        this.operation = BeContext.MpOperations.SEARCH
        exerciseService.searchExercise(this, request)
    }
}