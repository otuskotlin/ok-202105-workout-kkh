package ru.otus.otuskotlin.workout

import ru.otus.otuskotlin.workout.be.service.openapi.exceptions.ExerciseService
import ru.otus.otuskotlin.workout.be.repo.inmemory.models.RepoExerciseInMemory
import ru.otus.otuskotlin.workout.backend.logics.ExerciseCrud
import ru.otus.otuskotlin.workout.backend.common.context.ContextConfig
import ru.otus.otuskotlin.workout.backend.common.repo.common.exercise.IRepoExercise
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