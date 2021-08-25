package ru.otus.otuskotlin.workout

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import ru.otus.otuskotlin.workout.openapi.models.BaseDebugRequest
import ru.otus.otuskotlin.workout.openapi.models.Permissions
import ru.otus.otuskotlin.workout.openapi.models.ResponseExercise

object Utils {
    val mapper = jacksonObjectMapper()

    val stubDebug = BaseDebugRequest(mode = BaseDebugRequest.Mode.STUB)

    val stubResponseExercise = ResponseExercise(
        title = ExerciseStub.getModelExercise().title,
        description = ExerciseStub.getModelExercise().description,
        targetMuscleGroup = ExerciseStub.getModelExercise().targetMuscleGroup,
        synergisticMuscleGroup = ExerciseStub.getModelExercise().synergisticMuscleGroup,
        executionTechnique = ExerciseStub.getModelExercise().executionTechnique,
        id = ExerciseStub.getModelExercise().idExercise.asString(),
        permissions = ExerciseStub.getModelExercise().permissions.takeIf { it.isNotEmpty() }
            ?.map { Permissions.valueOf(it.toString()) }?.toSet()
    )
}