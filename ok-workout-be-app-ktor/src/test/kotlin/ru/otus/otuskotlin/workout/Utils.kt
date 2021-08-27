package ru.otus.otuskotlin.workout

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import ru.otus.otuskotlin.workout.openapi.models.*
import ru.workout.otuskotlin.workout.backend.mapping.openapi.toTransport

object Utils {
    val mapper = jacksonObjectMapper()
    val stubDebug = BaseDebugRequest(mode = BaseDebugRequest.Mode.STUB)

    val stubResponseExercise = ExerciseStub.getModelExercise().toTransport()
    val stubResponseWorkout = WorkoutStub.getModelWorkout().toTransport()

    val stubResponseSearchWorkout = listOf(stubResponseWorkout)
}
