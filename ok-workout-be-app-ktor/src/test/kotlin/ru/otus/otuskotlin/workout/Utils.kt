package ru.otus.otuskotlin.workout

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import ru.otus.otuskotlin.workout.openapi.models.*
import ru.workout.otuskotlin.workout.backend.mapping.openapi.toTransport

object Utils {
    val mapper = jacksonObjectMapper()
    val stubDebugSuccess =
        BaseDebugRequest(
            mode = BaseDebugRequest.Mode.STUB,
            stubCase = BaseDebugRequest.StubCase.SUCCESS
        )

    val stubResponseExercise = ExerciseStub.getModelExercise().toTransport()
    val stubResponseWorkout = WorkoutStub.getModelWorkout().toTransport()

    val stubResponseSearchWorkout = listOf(stubResponseWorkout)

    val stubCreatableExercise = CreatableExercise(
        title = stubResponseExercise.title,
        description = stubResponseExercise.description,
        targetMuscleGroup = stubResponseExercise.targetMuscleGroup,
        synergisticMuscleGroup = stubResponseExercise.synergisticMuscleGroup,
        executionTechnique = stubResponseExercise.executionTechnique
    )
}
