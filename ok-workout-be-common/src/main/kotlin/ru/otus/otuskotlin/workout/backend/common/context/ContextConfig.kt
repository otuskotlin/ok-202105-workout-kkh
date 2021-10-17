package ru.otus.otuskotlin.workout.backend.common.context

import ru.otus.otuskotlin.workout.backend.common.repo.common.exercise.IRepoExercise

data class ContextConfig(
    val repoExerciseProd: IRepoExercise = IRepoExercise.NONE,
    val repoExerciseTest: IRepoExercise = IRepoExercise.NONE
)
