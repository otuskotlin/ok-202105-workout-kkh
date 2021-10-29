package ru.otus.otuskotlin.workout.configs

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import ru.otus.otuskotlin.workout.be.service.openapi.exceptions.ExerciseService
import ru.otus.otuskotlin.workout.be.repo.inmemory.models.RepoExerciseInMemory
import ru.otus.otuskotlin.workout.backend.logics.ExerciseCrud
import ru.otus.otuskotlin.workout.backend.common.context.ContextConfig
import ru.otus.otuskotlin.workout.backend.repo.common.exercise.IRepoExercise
import ru.otus.otuskotlin.workout.controllers.KtorUserSession
import java.time.Duration

data class AppKtorConfig(
    val auth: KtorAuthConfig = KtorAuthConfig.TEST,
    val objectMapper: ObjectMapper = jacksonObjectMapper(),
    val userSessions: MutableSet<KtorUserSession> = mutableSetOf(),
    val exerciseRepoTest: IRepoExercise = RepoExerciseInMemory(initObjects = listOf()),
    val exerciseRepoProd: IRepoExercise = RepoExerciseInMemory(initObjects = listOf(), Duration.ofHours(1)),
    val contextConfig: ContextConfig = ContextConfig(
        repoExerciseProd = exerciseRepoProd,
        repoExerciseTest = exerciseRepoTest
    ),
    val exerciseCrud: ExerciseCrud = ExerciseCrud(contextConfig),
    val exerciseService: ExerciseService = ExerciseService(exerciseCrud)
)