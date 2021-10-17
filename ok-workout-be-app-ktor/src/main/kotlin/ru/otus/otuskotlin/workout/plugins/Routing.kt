package ru.otus.otuskotlin.workout.plugins

import com.fasterxml.jackson.databind.ObjectMapper
import ru.otus.otuskotlin.workout.be.service.openapi.exceptions.ExerciseService
import ru.otus.otuskotlin.workout.be.service.openapi.exceptions.WorkoutService
import io.ktor.application.*
import io.ktor.response.*
import io.ktor.routing.*
import ru.otus.otuskotlin.workout.backend.logics.WorkoutCrud
import ru.otus.otuskotlin.workout.controllers.KtorUserSession
import ru.otus.otuskotlin.workout.exercise
import ru.otus.otuskotlin.workout.websocketExercise
import ru.otus.otuskotlin.workout.websocketWorkout
import ru.otus.otuskotlin.workout.workout

fun Application.configRouting(
    exerciseService: ExerciseService,
    objectMapper: ObjectMapper,
    userSessions: MutableSet<KtorUserSession>
) {
    val crudWorkout = WorkoutCrud()
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