package ru.workout.otuskotlin.workout.backend.common.models

data class SearchWorkoutModel(
    var workoutDate: String = "",
    /* Text of search of a muscle group */
    var searchMuscleGroup: String = "",
    /* Text of search of an exercise in workouts */
    var searchExercise: String = ""
)
