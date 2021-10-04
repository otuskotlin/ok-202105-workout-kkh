package ru.otus.otuskotlin.workout

import ExerciseService
import WorkoutService
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import io.ktor.application.*
import io.ktor.routing.*
import io.ktor.websocket.*
import ru.otus.otuskotlin.workout.controllers.*

val objectMapper = jacksonObjectMapper()

fun Routing.exercise(exerciseService: ExerciseService) = route("exercise") {
    post("init") {
        call.initExercise(exerciseService)
    }
    post("create") {
        call.createExercise(exerciseService)
    }
    post("read") {
        call.readExercise(exerciseService)
    }
    post("update") {
        call.updateExercise(exerciseService)
    }
    post("delete") {
        call.deleteExercise(exerciseService)
    }
    post("search") {
        call.searchExercise(exerciseService)
    }
}

fun Routing.workout(workoutService: WorkoutService) = route("workout") {
    post("init") {
        call.initWorkout(workoutService)
    }
    post("create") {
        call.createWorkout(workoutService)
    }
    post("read") {
        call.readWorkout(workoutService)
    }
    post("update") {
        call.updateWorkout(workoutService)
    }
    post("delete") {
        call.deleteWorkout(workoutService)
    }
    post("search") {
        call.searchWorkout(workoutService)
    }
    post("chainOfExercises") {
        call.chainOfExercises(workoutService)
    }
}

fun Routing.websocketExercise(objectMapper: ObjectMapper, exerciseService: ExerciseService) {
    webSocket("exercise/ws") {
        handleSessionOfExercise(objectMapper, exerciseService)
    }
}

fun Routing.websocketWorkout(objectMapper: ObjectMapper, workoutService: WorkoutService) {
    webSocket("workout/ws") {
        handleSessionOfWorkout(objectMapper, workoutService)
    }
}
