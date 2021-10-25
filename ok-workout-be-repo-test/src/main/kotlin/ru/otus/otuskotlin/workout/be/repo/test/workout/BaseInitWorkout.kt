package ru.otus.otuskotlin.workout.be.repo.test.workout

import ru.otus.otuskotlin.workout.be.repo.test.IInitObjects
import ru.otus.otuskotlin.workout.backend.common.models.ExercisePermissions
import ru.otus.otuskotlin.workout.backend.common.models.WorkoutIdModel
import ru.otus.otuskotlin.workout.backend.common.models.WorkoutModel
import java.time.Instant
import java.util.*

abstract class BaseInitWorkout : IInitObjects<WorkoutModel> {
    fun createInitTestModel(
        suf: String
    ) = WorkoutModel(
        workoutDate = Instant.now(),
        duration = 0.0,
        recoveryTime = 0.0,
        modificationWorkout = WorkoutModel.ModificationWorkout.CLASSIC,
        exercisesBlock = mutableListOf(),
        idWorkout = WorkoutIdModel(UUID.randomUUID()),
        permissions = mutableSetOf(ExercisePermissions.READ),
    )
}