package ru.workout.otuskotlin.workout.backend.common.models

data class WorkoutModel(
    var date: String = "",
    /* Duration of workout */
    var duration: Double = 0.0,
    /* Recovery time between exercises */
    var recoveryTime: Double = 0.0,
    var modificationWorkout: ModificationWorkout = ModificationWorkout.CLASSIC,
    var exercises: MutableList<ExercisesBlockModel> = mutableListOf()
) {
    enum class ModificationWorkout {
        CLASSIC, CIRCUIT;
    }
}