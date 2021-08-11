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

fun BeContext.toCreateExerciseResponse() = CreateExerciseResponse(
    requestId = requestId.takeIf { it.isNotBlank() },
    result = if (errors.find { it.level == IError.Level.ERROR } == null) CreateExerciseResponse.Result.SUCCESS
    else CreateExerciseResponse.Result.ERROR,
    errors = errors.takeIf { it.isNotEmpty() }?.map { it.toTransport() },
    createdExercise = responseExercise.takeIf { it != ExerciseModel() }?.toTransport()
)

fun BeContext.toReadExerciseResponse() = ReadExerciseResponse(
    requestId = requestId.takeIf { it.isNotBlank() },
    result = if (errors.find { it.level == IError.Level.ERROR } == null) ReadExerciseResponse.Result.SUCCESS
    else ReadExerciseResponse.Result.ERROR,
    errors = errors.takeIf { it.isNotEmpty() }?.map { it.toTransport() },
    readExercise = responseExercise.takeIf { it != ExerciseModel() }?.toTransport()
)

fun BeContext.toUpdateExerciseResponse() = UpdateExerciseResponse(
    requestId = requestId.takeIf { it.isNotBlank() },
    result = if (errors.find { it.level == IError.Level.ERROR } == null) UpdateExerciseResponse.Result.SUCCESS
    else UpdateExerciseResponse.Result.ERROR,
    errors = errors.takeIf { it.isNotEmpty() }?.map { it.toTransport() },
    updateExercise = responseExercise.takeIf { it != ExerciseModel() }?.toTransport()
)

fun BeContext.toDeleteExerciseResponse() = DeleteExerciseResponse(
    requestId = requestId.takeIf { it.isNotBlank() },
    result = if (errors.find { it.level == IError.Level.ERROR } == null) DeleteExerciseResponse.Result.SUCCESS
    else DeleteExerciseResponse.Result.ERROR,
    errors = errors.takeIf { it.isNotEmpty() }?.map { it.toTransport() },
    deleteExercise = responseExercise.takeIf { it != ExerciseModel() }?.toTransport()
)

fun BeContext.toSearchExerciseResponse() = SearchExerciseResponse(
    requestId = requestId.takeIf { it.isNotBlank() },
    result = if (errors.find { it.level == IError.Level.ERROR } == null) SearchExerciseResponse.Result.SUCCESS
    else SearchExerciseResponse.Result.ERROR,
    errors = errors.takeIf { it.isNotEmpty() }?.map { it.toTransport() },
    foundExercises = foundExercises.takeIf { it.isNotEmpty() }?.map { it.toTransport() }
)

fun BeContext.toInitWorkoutResponse() = InitWorkoutResponse(
    requestId = requestId.takeIf { it.isNotBlank() },
    result = if (errors.find { it.level == IError.Level.ERROR } == null) InitWorkoutResponse.Result.SUCCESS
    else InitWorkoutResponse.Result.ERROR,
    errors = errors.takeIf { it.isNotEmpty() }?.map { it.toTransport() }
)

fun BeContext.toCreateWorkoutResponse() = CreateWorkoutResponse(
    requestId = requestId.takeIf { it.isNotBlank() },
    result = if (errors.find { it.level == IError.Level.ERROR } == null) CreateWorkoutResponse.Result.SUCCESS
    else CreateWorkoutResponse.Result.ERROR,
    errors = errors.takeIf { errors.isNotEmpty() }?.map { it.toTransport() },
    createdWorkout = responseWorkout.takeIf { it != WorkoutModel() }?.toTransport()
)

fun BeContext.toReadWorkoutResponse() = ReadWorkoutResponse(
    requestId = requestId.takeIf { it.isNotBlank() },
    result = if (errors.find { it.level == IError.Level.ERROR } == null) ReadWorkoutResponse.Result.SUCCESS
    else ReadWorkoutResponse.Result.ERROR,
    errors = errors.takeIf { errors.isNotEmpty() }?.map { it.toTransport() },
    readWorkout = responseWorkout.takeIf { it != WorkoutModel() }?.toTransport()
)

fun BeContext.toUpdateWorkoutResponse() = UpdateWorkoutResponse(
    requestId = requestId.takeIf { it.isNotBlank() },
    result = if (errors.find { it.level == IError.Level.ERROR } == null) UpdateWorkoutResponse.Result.SUCCESS
    else UpdateWorkoutResponse.Result.ERROR,
    errors = errors.takeIf { errors.isNotEmpty() }?.map { it.toTransport() },
    updateWorkout = responseWorkout.takeIf { it != WorkoutModel() }?.toTransport()
)

fun BeContext.toDeleteWorkoutResponse() = DeleteWorkoutResponse(
    requestId = requestId.takeIf { it.isNotBlank() },
    result = if (errors.find { it.level == IError.Level.ERROR } == null) DeleteWorkoutResponse.Result.SUCCESS
    else DeleteWorkoutResponse.Result.ERROR,
    errors = errors.takeIf { errors.isNotEmpty() }?.map { it.toTransport() },
    deleteWorkout = responseWorkout.takeIf { it != WorkoutModel() }?.toTransport()
)

fun BeContext.toSearchWorkoutResponse() = SearchWorkoutResponse(
    requestId = requestId.takeIf { it.isNotBlank() },
    result = if (errors.find { it.level == IError.Level.ERROR } == null) SearchWorkoutResponse.Result.SUCCESS
    else SearchWorkoutResponse.Result.ERROR,
    errors = errors.takeIf { errors.isNotEmpty() }?.map { it.toTransport() },
    foundWorkouts = foundWorkouts.takeIf { it.isNotEmpty() }?.map { it.toTransport() }
)

fun BeContext.toChainOfExercises() = ChainOfExerciseResponse(
    requestId = requestId.takeIf { it.isNotBlank() },
    result = if (errors.find { it.level == IError.Level.ERROR } == null) ChainOfExerciseResponse.Result.SUCCESS
    else ChainOfExerciseResponse.Result.ERROR,
    errors = errors.takeIf { errors.isNotEmpty() }?.map { it.toTransport() },
    chainOfExercise = responseExercises.takeIf { it.isNotEmpty() }?.map { it.toTransport() }
)

private fun IError.toTransport() = RequestError(
    message = message.takeIf { it.isNotBlank() },
    field = field.takeIf { it.isNotBlank() }
)

private fun ExerciseModel.toTransport() = ResponseExercise(
    title = title.takeIf { it.isNotBlank() },
    description = description.takeIf { it.isNotBlank() },
    targetMuscleGroup = targetMuscleGroup.takeIf { it.isNotEmpty() },
    synergisticMuscleGroup = synergisticMuscleGroup.takeIf { it.isNotEmpty() },
    executionTechnique = executionTechnique.takeIf { it.isNotBlank() },
    id = idExercise.takeIf { it != ExerciseIdModel.NONE }?.toString(),
    permissions = permissions.takeIf { it.isNotEmpty() }?.map { Permissions.valueOf(it.name) }?.toSet()
)

private fun WorkoutModel.toTransport() = ResponseWorkout(
    date = date.takeIf { date.isNotBlank() },
    duration = duration.takeIf { it > 0.0 } ?: 0.0,
    recoveryTime = recoveryTime.takeIf { it > 0.0 } ?: 0.0,
    modificationWorkout = ResponseWorkout.ModificationWorkout.valueOf(modificationWorkout.name),
    exercisesBlock = exercisesBlock.takeIf { it.isNotEmpty() }?.map { it.toTransport() }
)

private fun ExercisesBlockModel.toTransport() = ExercisesBlock(
    exercise = exercise.toTransport(),
    sets = sets.takeIf { it.isNotEmpty() }?.map { it.toTransport() },
    modificationBlockExercises = ExercisesBlock.ModificationBlockExercises.valueOf(modificationBlockExercises.name)
)

private fun OneSetModel.toTransport() = OneSet(
    performance = performance.takeIf { it.isNotEmpty() }?.map {
        Performance(
            weight = it.weight.takeIf { weight -> (weight > 0.0) } ?: 0.0,
            measure = Performance.Measure.valueOf(it.measure.name),
            repetition = it.repetition.takeIf { repetition -> (repetition > 0) } ?: 0
        )
    }
)