package ru.workout.otuskotlin.workout.backend.common.context

import ru.workout.otuskotlin.workout.backend.common.repo.common.exercise.IRepoExercise

data class ContextConfig(
    val repoExerciseProd: IRepoExercise = IRepoExercise.NONE,
    val repoExerciseTest: IRepoExercise = IRepoExercise.NONE
)
