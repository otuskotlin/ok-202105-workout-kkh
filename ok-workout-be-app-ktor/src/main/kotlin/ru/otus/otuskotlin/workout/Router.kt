package ru.otus.otuskotlin.workout

import ExerciseService
import WorkoutService
import io.ktor.application.*
import io.ktor.routing.*
import ru.otus.otuskotlin.workout.controllers.*

fun Routing.exercise(exerciseService: ExerciseService) = route("exercise") {
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
}
