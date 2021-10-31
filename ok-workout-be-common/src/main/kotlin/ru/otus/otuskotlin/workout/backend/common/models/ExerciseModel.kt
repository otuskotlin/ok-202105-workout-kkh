package ru.otus.otuskotlin.workout.backend.common.models

data class ExerciseModel(
    var title: String = "",
    var description: String = "",
    var authorId: AuthorIdModel = AuthorIdModel.NONE,
    var targetMuscleGroup: MutableList<String> = mutableListOf(),
    var synergisticMuscleGroup: MutableList<String> = mutableListOf(),
    var executionTechnique: String = "",
    var idExercise: ExerciseIdModel = ExerciseIdModel.NONE,
    var permissions: MutableSet<ExercisePermissions> = mutableSetOf()
)
