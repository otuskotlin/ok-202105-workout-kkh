package ru.workout.otuskotlin.workout.backend.common.models

import java.time.Instant
import java.util.*

data class SearchWorkoutModel(
    var date: Date = Date.from(Instant.now()),
    /* Text of search of a muscle group */
    var searchMuscleGroup: String = "",
    /* Text of search of an exercise in workouts */
    var searchExercise: String = ""
)
