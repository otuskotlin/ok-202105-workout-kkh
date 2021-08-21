package ru.workout.otuskotlin.workout.backend.common.models

data class WorkoutModel(
    var workoutDate: String = "",
    /* Duration of workout */
    var duration: Double = 0.0,
    /* Recovery time between exercises */
    var recoveryTime: Double = 0.0,
    var modificationWorkout: ModificationWorkout = ModificationWorkout.CLASSIC,
    var exercisesBlock: MutableList<ExercisesBlockModel> = mutableListOf(),
    var idWorkout: WorkoutIdModel = WorkoutIdModel.NONE,
    var permissions: MutableSet<ExercisePermissions> = mutableSetOf()
) {
    enum class ModificationWorkout {
        CLASSIC, CIRCUIT;
    }
}