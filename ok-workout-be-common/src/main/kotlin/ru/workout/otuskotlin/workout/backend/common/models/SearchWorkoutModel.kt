package ru.workout.otuskotlin.workout.backend.common.models

import java.time.Instant

data class SearchWorkoutModel(
    var workoutDate: Instant = Instant.now(),
    /* Text of search of a muscle group */
    var searchMuscleGroup: String = "",
    /* Text of search of an exercise in workouts */
    var searchExercise: String = ""
)
