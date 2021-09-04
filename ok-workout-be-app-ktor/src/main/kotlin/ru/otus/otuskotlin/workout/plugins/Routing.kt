package ru.otus.otuskotlin.workout.plugins

import ExerciseService
import WorkoutService
import io.ktor.application.*
import io.ktor.response.*
import io.ktor.routing.*
import ru.otus.otuskotlin.workout.backend.logics.ExerciseCrud
import ru.otus.otuskotlin.workout.exercise
import ru.otus.otuskotlin.workout.workout

fun Application.configRouting() {

    val crud = ExerciseCrud()
    val exerciseService = ExerciseService(crud)
    
    val workoutService = WorkoutService()

    routing {
        get("/") {
            call.respondText("Hello, Ktor!")
        }
        exercise(exerciseService)
        workout(workoutService)
    }
}