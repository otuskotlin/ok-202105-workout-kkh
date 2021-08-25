package ru.otus.otuskotlin.workout.plugins

import ExerciseService
import WorkoutService
import io.ktor.application.*
import io.ktor.response.*
import io.ktor.routing.*
import ru.otus.otuskotlin.workout.exercise
import ru.otus.otuskotlin.workout.workout

fun Application.configRouting() {

    val exerciseService = ExerciseService()
    val workoutService = WorkoutService()

    routing {
        get("/") {
            call.respondText("Hello, Ktor!")
        }
        exercise(exerciseService)
        workout(workoutService)
    }
}