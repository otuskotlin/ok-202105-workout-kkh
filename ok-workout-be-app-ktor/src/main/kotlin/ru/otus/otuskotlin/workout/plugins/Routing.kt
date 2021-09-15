package ru.otus.otuskotlin.workout.plugins

import ExerciseService
import WorkoutService
import io.ktor.application.*
import io.ktor.response.*
import io.ktor.routing.*
import ru.otus.otuskotlin.workout.backend.logics.ExerciseCrud
import ru.otus.otuskotlin.workout.backend.logics.WorkoutCrud
import ru.otus.otuskotlin.workout.exercise
import ru.otus.otuskotlin.workout.workout

fun Application.configRouting() {

    val crudExercise = ExerciseCrud()
    val crudWorkout = WorkoutCrud()
    val exerciseService = ExerciseService(crudExercise)
    
    val workoutService = WorkoutService(crudWorkout)

    routing {
        get("/") {
            call.respondText("Hello, Ktor!")
        }
        exercise(exerciseService)
        workout(workoutService)
    }
}