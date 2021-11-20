package ru.otus.otuskotlin.workout.controllers

import ru.otus.otuskotlin.workout.be.service.openapi.exceptions.ExerciseService
import io.ktor.application.*
import ru.otus.otuskotlin.workout.helpers.handleRoute
import ru.otus.otuskotlin.workout.logging.mpLogger
import ru.otus.otuskotlin.workout.openapi.models.*

suspend fun ApplicationCall.initExercise(exerciseService: ExerciseService) {

    handleRoute<InitExerciseRequest, InitExerciseResponse>(
        "init-exercise",
        mpLogger(this::initExercise::class.java)
    ) { request ->
        exerciseService.initExercise(this, request)
    }
}

suspend fun ApplicationCall.createExercise(exerciseService: ExerciseService) {
    handleRoute<CreateExerciseRequest, CreateExerciseResponse>(
        "create-exercise",
        mpLogger(this::createExercise::class.java)
    ) { request ->
        exerciseService.createExercise(this, request)
    }
}

suspend fun ApplicationCall.readExercise(exerciseService: ExerciseService) {
    handleRoute<ReadExerciseRequest, ReadExerciseResponse>(
        "read-exercise",
        mpLogger(this::readExercise::class.java)
    ) { request ->
        exerciseService.readExercise(this, request)
    }
}

suspend fun ApplicationCall.updateExercise(exerciseService: ExerciseService) {
    handleRoute<UpdateExerciseRequest, UpdateExerciseResponse>(
        "update-exercise",
        mpLogger(this::updateExercise::class.java)
    ) { request ->
        exerciseService.updateExercise(this, request)
    }
}

suspend fun ApplicationCall.deleteExercise(exerciseService: ExerciseService) {
    handleRoute<DeleteExerciseRequest, DeleteExerciseResponse>(
        "delete-exercise",
        mpLogger(this::deleteExercise::class.java)
    ) { request ->
        exerciseService.deleteExercise(this, request)
    }
}

suspend fun ApplicationCall.searchExercise(exerciseService: ExerciseService) {
    handleRoute<SearchExerciseRequest, SearchExerciseResponse>(
        "search-exercise",
        mpLogger(this::searchExercise::class.java)
    ) { request ->
        exerciseService.searchExercise(this, request)
    }
}