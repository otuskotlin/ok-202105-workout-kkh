package ru.otus.otuskotlin.workout

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import ru.otus.otuskotlin.workout.openapi.models.*
import ru.workout.otuskotlin.workout.backend.common.convertToString

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

    val stubResponseWorkout: ResponseWorkout = ResponseWorkout(
        date = WorkoutStub.getModelWorkout().workoutDate.convertToString(),
        duration = WorkoutStub.getModelWorkout().duration,
        recoveryTime = WorkoutStub.getModelWorkout().recoveryTime,
        modificationWorkout = ResponseWorkout.ModificationWorkout.valueOf(WorkoutStub.getModelWorkout().modificationWorkout.name),
        exercisesBlock = WorkoutStub.getModelWorkout().exercisesBlock.map { exercisesBlockModel ->
            ExercisesBlock(
                exercise = ResponseExercise(
                    title = exercisesBlockModel.exercise.title,
                    description = exercisesBlockModel.exercise.description,
                    targetMuscleGroup = exercisesBlockModel.exercise.targetMuscleGroup,
                    synergisticMuscleGroup = exercisesBlockModel.exercise.synergisticMuscleGroup,
                    executionTechnique = exercisesBlockModel.exercise.executionTechnique,
                    id = exercisesBlockModel.exercise.idExercise.asString(),
                    permissions = exercisesBlockModel.exercise.permissions.map { permission ->
                        Permissions.valueOf(
                            permission.name
                        )
                    }
                        .toSet()
                )
            )
        },
        id = WorkoutStub.getModelWorkout().idWorkout.asString(),
        permissions = WorkoutStub.getModelWorkout().permissions.map { permission -> Permissions.valueOf(permission.name) }
            .toSet()
    )
}
