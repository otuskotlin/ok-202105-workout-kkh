package ru.otus.otuskotlin.workout.plugins

import ExerciseService
import WorkoutService
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import io.ktor.application.*
import io.ktor.response.*
import io.ktor.routing.*
import ru.otus.otuskotlin.workout.backend.logics.ExerciseCrud
import ru.otus.otuskotlin.workout.backend.logics.WorkoutCrud
import ru.otus.otuskotlin.workout.controllers.KtorUserSession
import ru.otus.otuskotlin.workout.exercise
import ru.otus.otuskotlin.workout.websocketExercise
import ru.otus.otuskotlin.workout.websocketWorkout
import ru.otus.otuskotlin.workout.workout

fun Application.configRouting() {

    val crudExercise = ExerciseCrud()
    val crudWorkout = WorkoutCrud()
    val exerciseService = ExerciseService(crudExercise)
    val objectMapper = jacksonObjectMapper()

    val userSessions = mutableSetOf<KtorUserSession>()

    val workoutService = WorkoutService(crudWorkout)

    routing {
        get("/") {
            call.respondText("Hello, Ktor!")
        }

        exercise(exerciseService)
        workout(workoutService)
        websocketExercise(objectMapper, exerciseService, userSessions)
        websocketWorkout(objectMapper, workoutService, userSessions)
    }
}