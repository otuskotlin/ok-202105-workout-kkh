package ru.otus.otuskotlin.workout.plugins

import ExerciseService
import RepoExerciseInMemory
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
import ru.otus.otuskotlin.workout.backend.common.context.ContextConfig
import ru.otus.otuskotlin.workout.backend.common.repo.common.exercise.IRepoExercise
import java.time.Duration

fun Application.configRouting() {
    val crudWorkout = WorkoutCrud()
    val objectMapper = jacksonObjectMapper()
    val exerciseRepoTest: IRepoExercise = RepoExerciseInMemory(initObjects = listOf())
    val exerciseRepoProd: IRepoExercise = RepoExerciseInMemory(initObjects = listOf(), ttl = Duration.ofHours(1))
    val contextConfig = ContextConfig(
        repoExerciseProd = exerciseRepoProd,
        repoExerciseTest = exerciseRepoTest
    )
    val crudExercise = ExerciseCrud(contextConfig)
    val exerciseService = ExerciseService(crudExercise)
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