package ru.otus.otuskotlin.workout.be.repo.test

import ru.workout.otuskotlin.workout.backend.common.models.ExerciseIdModel
import ru.workout.otuskotlin.workout.backend.common.models.ExerciseModel
import ru.workout.otuskotlin.workout.backend.common.models.ExercisePermissions
import java.util.*

abstract class BaseInitExercise : IInitObjects<ExerciseModel> {
    fun createInitTestModel(
        suf: String
    ) = ExerciseModel(
        title = "$suf stub title",
        description = "$suf stub description",
        targetMuscleGroup = mutableListOf("$suf stub target muscle group"),
        synergisticMuscleGroup = mutableListOf("$suf stub synergistic muscle group"),
        executionTechnique = "$suf stub execution technique",
        idExercise = ExerciseIdModel(UUID.randomUUID()),
        permissions = mutableSetOf(ExercisePermissions.READ)
    )
}
