package ru.otus.plugins

import ExerciseService
import WorkoutService
import io.ktor.application.*
import io.ktor.response.*
import io.ktor.routing.*
import ru.otus.exercise
import ru.otus.workout

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