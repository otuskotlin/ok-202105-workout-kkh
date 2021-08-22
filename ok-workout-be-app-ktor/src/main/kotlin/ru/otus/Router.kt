package ru.otus

import io.ktor.application.*
import io.ktor.routing.*
import ru.otus.controllers.*
import ru.otus.services.ExerciseService

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
