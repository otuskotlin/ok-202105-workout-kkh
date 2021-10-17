package ru.otus.otuskotlin.workout.backend.common.models

import java.time.Instant

data class WorkoutModel(
    var workoutDate: Instant = Instant.now(),
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