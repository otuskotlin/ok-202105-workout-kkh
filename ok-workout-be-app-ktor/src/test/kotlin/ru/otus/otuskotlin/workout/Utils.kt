package ru.otus.otuskotlin.workout

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import ru.otus.otuskotlin.workout.Utils.stubCreatableWorkout
import ru.otus.otuskotlin.workout.Utils.stubResponseWorkout
import ru.otus.otuskotlin.workout.openapi.models.*
import ru.workout.otuskotlin.workout.backend.common.models.WorkoutModel
import ru.workout.otuskotlin.workout.backend.mapping.openapi.toTransport

object Utils {
    val mapper = jacksonObjectMapper()
    val stubDebugSuccess =
        BaseDebugRequest(
            mode = BaseDebugRequest.Mode.STUB,
            stubCase = BaseDebugRequest.StubCase.SUCCESS
        )

    val stubResponseExercise = ExerciseStub.getModelExercise().toTransport()
//    val stubResponseWorkout = ResponseWorkout(
//        date = WorkoutStub.getModelWorkout().workoutDate.toString(),
//        duration = WorkoutStub.getModelWorkout().duration,
//        recoveryTime = WorkoutStub.getModelWorkout().recoveryTime,
//        modificationWorkout = ResponseWorkout.ModificationWorkout
//            .valueOf(WorkoutStub.getModelWorkout().modificationWorkout.name),
//        exercisesBlock = WorkoutStub.getModelWorkout().exercisesBlock.map {
//            ExercisesBlock()
//        },
//        id = WorkoutStub.getModelWorkout().idWorkout.asString(),
//        permissions = WorkoutStub.getModelWorkout().permissions.map { Permissions.valueOf(it.name) }.toSet()
//    )
    val stubResponseWorkout = WorkoutStub.getModelWorkout().toTransport()

    val stubResponseWorkoutTwo = WorkoutStub.getModelWorkoutTwo().toTransport()

    val stubResponseSearchWorkout = listOf(stubResponseWorkout, stubResponseWorkoutTwo)

    val stubCreatableExercise = CreatableExercise(
        title = stubResponseExercise.title,
        description = stubResponseExercise.description,
        targetMuscleGroup = stubResponseExercise.targetMuscleGroup,
        synergisticMuscleGroup = stubResponseExercise.synergisticMuscleGroup,
        executionTechnique = stubResponseExercise.executionTechnique,
    )
    val stubUpdatableExercise = UpdatableExercise(
        title = stubResponseExercise.title,
        description = stubResponseExercise.description,
        targetMuscleGroup = stubResponseExercise.targetMuscleGroup,
        synergisticMuscleGroup = stubResponseExercise.synergisticMuscleGroup,
        executionTechnique = stubResponseExercise.executionTechnique,
        id = stubResponseExercise.id
    )

    val stubCreatableWorkout = CreatableWorkout(
        date = stubResponseWorkout.date,
        duration = stubResponseWorkout.duration,
        recoveryTime = stubResponseWorkout.recoveryTime,
        modificationWorkout = stubResponseWorkout.modificationWorkout?.let {
            CreatableWorkout.ModificationWorkout.valueOf(
                it.name
            )
        },
        exercisesBlock = stubResponseWorkout.exercisesBlock
    )

    val stubUpdatableWorkout = UpdatableWorkout(
        date = stubResponseWorkout.date,
        duration = stubResponseWorkout.duration,
        recoveryTime = stubResponseWorkout.recoveryTime,
        modificationWorkout = stubResponseWorkout.modificationWorkout?.let {
            UpdatableWorkout.ModificationWorkout.valueOf(
                it.name
            )
        },
        exercisesBlock = stubResponseWorkout.exercisesBlock,
        id = stubResponseWorkout.id
    )
}

fun main() {
    println(stubResponseWorkout)
}
