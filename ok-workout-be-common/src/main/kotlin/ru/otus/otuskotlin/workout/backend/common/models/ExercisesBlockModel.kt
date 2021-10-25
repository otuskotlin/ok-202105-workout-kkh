package ru.otus.otuskotlin.workout.backend.common.models

data class ExercisesBlockModel(
    var exercise: ExerciseModel = ExerciseModel(),
    var sets: MutableList<OneSetModel> = mutableListOf(),
    var modificationBlockExercises: ModificationBlockExercises = ModificationBlockExercises.NONE
)
