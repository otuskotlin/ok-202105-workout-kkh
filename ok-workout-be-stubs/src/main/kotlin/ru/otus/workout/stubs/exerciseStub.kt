package ru.otus.workout.stubs

import ru.workout.otuskotlin.workout.backend.common.models.ExerciseIdModel
import ru.workout.otuskotlin.workout.backend.common.models.ExerciseModel

object Exercise {
    private val stubReady = ExerciseModel(
        title = "",
        description = "",
        targetMuscleGroup = mutableListOf("Квадрицепсы"),
        synergisticMuscleGroup = mutableListOf(
            "Большие ягодичные",
            "Приводящие бедра",
            "Камбаловидные"
        ),
        executionTechnique = "Выполняющий упражнение приседает и затем встаёт, возвращаясь в положение стоя",
        idExercise = ExerciseIdModel(id = "eID:0001")
    )

    fun getModelExercise() = stubReady
}