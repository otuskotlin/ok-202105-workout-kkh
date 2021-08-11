package ru.workout.otuskotlin.workout.backend.mapping.openapi

import ru.otus.otuskotlin.workout.openapi.models.*
import ru.otus.otuskotlin.workout.openapi.models.Performance
import ru.workout.otuskotlin.workout.backend.common.context.BeContext
import ru.workout.otuskotlin.workout.backend.common.models.*

fun BeContext.toInitExerciseResponse() = InitExerciseResponse(
    requestId = requestId.takeIf { it.isNotBlank() },
    result = if (errors.find { it.level == IError.Level.ERROR } == null) InitExerciseResponse.Result.SUCCESS
    else InitExerciseResponse.Result.ERROR,
    errors = errors.takeIf { it.isNotEmpty() }?.map { it.toTransport() }
)

fun BeContext.toReadExerciseResponse() = ReadExerciseResponse(
    requestId = requestId.takeIf { it.isNotBlank() },
    result = if (errors.find { it.level == IError.Level.ERROR } == null) ReadExerciseResponse.Result.SUCCESS
    else ReadExerciseResponse.Result.ERROR,
    errors = errors.takeIf { it.isNotEmpty() }?.map { it.toTransport() },
    readExercise = responseExercise.takeIf { it != ExerciseModel() }?.toTransport()
)

fun BeContext.toInitWorkoutResponse() = InitWorkoutResponse(
    requestId = requestId.takeIf { it.isNotBlank() },
    result = if (errors.find { it.level == IError.Level.ERROR } == null) InitWorkoutResponse.Result.SUCCESS
    else InitWorkoutResponse.Result.ERROR,
    errors = errors.takeIf { it.isNotEmpty() }?.map { it.toTransport() }
)

fun BeContext.toReadWorkoutResponse() = ReadWorkoutResponse(
    requestId = requestId.takeIf { it.isNotBlank() },
    result = if (errors.find { it.level == IError.Level.ERROR } == null) ReadWorkoutResponse.Result.SUCCESS
    else ReadWorkoutResponse.Result.ERROR,
    errors = errors.takeIf { errors.isNotEmpty() }?.map { it.toTransport() },
    readWorkout = responseWorkout.takeIf { it != WorkoutModel() }?.toTransport()
)

fun IError.toTransport() = RequestError(
    message = message.takeIf { it.isNotBlank() },
    field = field.takeIf { it.isNotBlank() }
)

fun ExerciseModel.toTransport() = ResponseExercise(
    title = title.takeIf { it.isNotBlank() },
    description = description.takeIf { it.isNotBlank() },
    targetMuscleGroup = targetMuscleGroup.takeIf { it.isNotEmpty() },
    synergisticMuscleGroup = synergisticMuscleGroup.takeIf { it.isNotEmpty() },
    executionTechnique = executionTechnique.takeIf { it.isNotBlank() },
    id = idExercise.takeIf { it != ExerciseIdModel.NONE }?.toString(),
    permissions = permissions.takeIf { it.isNotEmpty() }?.map { Permissions.valueOf(it.name) }?.toSet()
)

fun WorkoutModel.toTransport() = ResponseWorkout(
    date = date.takeIf { date.isNotBlank() },
    duration = duration.takeIf { it > 0.0 } ?: 0.0,
    recoveryTime = recoveryTime.takeIf { it > 0.0 } ?: 0.0,
    modificationWorkout = ResponseWorkout.ModificationWorkout.valueOf(modificationWorkout.name),
    exercisesBlock = exercisesBlock.takeIf { it.isNotEmpty() }?.map { it.toTransport() }
)

fun ExercisesBlockModel.toTransport() = ExercisesBlock(
    exercise = exercise.toCreatableExercise(),
    sets = sets.takeIf { it.isNotEmpty() }?.map { it.toTransport() },
    modificationBlockExercises = ExercisesBlock.ModificationBlockExercises.valueOf(modificationBlockExercises.name)
)

fun ExerciseModel.toCreatableExercise() = CreatableExercise(
    title = title.takeIf { it.isNotBlank() },
    description = description.takeIf { it.isNotBlank() },
    targetMuscleGroup = targetMuscleGroup.takeIf { it.isNotEmpty() },
    synergisticMuscleGroup = synergisticMuscleGroup.takeIf { it.isNotEmpty() },
    executionTechnique = executionTechnique.takeIf { it.isNotBlank() }
)

fun OneSetModel.toTransport() = OneSet(
    performance = performance.takeIf { it.isNotEmpty() }?.map {
        Performance(
            weight = it.weight.takeIf { weight -> (weight > 0.0) } ?: 0.0,
            measure = Performance.Measure.valueOf(it.measure.name),
            repetition = it.repetition.takeIf { repetition -> (repetition > 0) } ?: 0
        )
    }
)