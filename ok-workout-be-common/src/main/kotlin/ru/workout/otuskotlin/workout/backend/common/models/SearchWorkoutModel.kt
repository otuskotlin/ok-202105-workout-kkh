package ru.workout.otuskotlin.workout.backend.common.models

import java.time.LocalDate

data class SearchWorkoutModel(
    var date: LocalDate = LocalDate.now(),
    /* Text of search of a muscle group */
    var searchMuscleGroup: String = "",
    /* Text of search of an exercise in workouts */
    var searchExercise: String = ""
)
