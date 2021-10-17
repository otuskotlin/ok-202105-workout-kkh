package ru.otus.otuskotlin.workout.be.repo.test.exercise

import ru.otus.otuskotlin.workout.be.repo.test.IInitObjects
import ru.otus.otuskotlin.workout.backend.common.models.ExerciseIdModel
import ru.otus.otuskotlin.workout.backend.common.models.ExerciseModel
import java.util.*

abstract class BaseInitExercise : IInitObjects<ExerciseModel> {
    fun createInitTestModel(
        suf: String,
        title: String = "stub title",
        description: String = "stub description"
    ) = ExerciseModel(
        title = "$suf $title",
        description = "$suf $description",
        targetMuscleGroup = mutableListOf("$suf stub target muscle group"),
        synergisticMuscleGroup = mutableListOf("$suf stub synergistic muscle group"),
        executionTechnique = "$suf stub execution technique",
        idExercise = ExerciseIdModel(UUID.randomUUID()),
    )
}
