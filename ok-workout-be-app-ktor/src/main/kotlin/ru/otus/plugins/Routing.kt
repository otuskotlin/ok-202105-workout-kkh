package ru.otus.plugins

import io.ktor.application.*
import io.ktor.response.*
import io.ktor.routing.*
import ru.otus.exercise
import ru.otus.services.ExerciseService
import ru.otus.services.WorkoutService
import ru.otus.workout

fun Application.configRouting() {

    val exerciseService = ExerciseService()
    val workoutService = WorkoutService()

    routing {
        get("/") {
            call.respondText("Hello, world!")
        }
        exercise(exerciseService)
        workout(workoutService)
    }
}