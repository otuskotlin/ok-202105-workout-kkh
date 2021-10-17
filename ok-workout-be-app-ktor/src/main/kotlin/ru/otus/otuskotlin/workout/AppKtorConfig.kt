package ru.otus.otuskotlin.workout

import ExerciseService
import RepoExerciseInMemory
import ru.otus.otuskotlin.workout.backend.logics.ExerciseCrud
import ru.workout.otuskotlin.workout.backend.common.context.ContextConfig
import ru.workout.otuskotlin.workout.backend.common.repo.common.exercise.IRepoExercise
import java.time.Duration

data class AppKtorConfig(
    val exerciseRepoTest: IRepoExercise = RepoExerciseInMemory(initObjects = listOf()),
    val exerciseRepoProd: IRepoExercise = RepoExerciseInMemory(initObjects = listOf(), Duration.ofHours(1)),
    val contextConfig: ContextConfig = ContextConfig(
        repoExerciseProd = exerciseRepoProd,
        repoExerciseTest = exerciseRepoTest
    ),
    val exerciseCrud: ExerciseCrud = ExerciseCrud(),
    val exerciseService: ExerciseService = ExerciseService(exerciseCrud)
)