package ru.workout.otuskotlin.workout.backend.mapping.openapi

import ru.otus.otuskotlin.workout.openapi.models.*
import ru.otus.otuskotlin.workout.openapi.models.Performance
import ru.workout.otuskotlin.workout.backend.common.context.BeContext
import ru.workout.otuskotlin.workout.backend.common.models.*
import java.time.Instant

fun BeContext.setQuery(query: InitExerciseRequest) = apply {
    requestId = query.requestId ?: ""
}

fun BeContext.setQuery(query: CreateExerciseRequest) = apply {
    requestId = query.requestId ?: ""
    requestExercise = query.createExercise?.toModel() ?: ExerciseModel()
}

fun BeContext.setQuery(query: ReadExerciseRequest) = apply {
    requestId = query.requestId ?: ""
    requestExerciseId = ExerciseIdModel(query.readExerciseId ?: "")
}

fun BeContext.setQuery(query: UpdateExerciseRequest) = apply {
    requestId = query.requestId ?: ""
    requestExercise = query.updateExercise?.toModel() ?: ExerciseModel()
}

fun BeContext.setQuery(query: DeleteExerciseRequest) = apply {
    requestId = query.requestId ?: ""
    requestExerciseId = ExerciseIdModel(query.deleteExerciseId ?: "")
}

fun BeContext.setQuery(query: SearchExerciseRequest) = apply {
    requestId = query.requestId ?: ""
    requestSearchExercise = query.search ?: ""
}

fun BeContext.setQuery(query: InitWorkoutRequest) = apply {
    requestId = query.requestId ?: ""
}

fun BeContext.setQuery(query: CreateWorkoutRequest) = apply {
    requestId = query.requestId ?: ""
    requestWorkout = query.createWorkout?.toModel() ?: WorkoutModel()
}

fun BeContext.setQuery(query: ReadWorkoutRequest) = apply {
    requestId = query.requestId ?: ""
    requestWorkoutId = WorkoutIdModel(query.readWorkoutId ?: "")
}

fun BeContext.setQuery(query: UpdateWorkoutRequest) = apply {
    requestId = query.requestId ?: ""
    requestWorkout = query.updateWorkout?.toModel() ?: WorkoutModel()
}

fun BeContext.setQuery(query: DeleteWorkoutRequest) = apply {
    requestId = query.requestId ?: ""
    requestWorkoutId = WorkoutIdModel(query.deleteWorkoutId ?: "")
}

fun BeContext.setQuery(query: SearchWorkoutRequest) = apply {
    requestId = query.requestId ?: ""
    requestSearchWorkout.apply {
        workoutDate = query.date ?: ""
        searchMuscleGroup = query.searchMuscleGroup ?: ""
        searchExercise = query.searchExercise ?: ""
    }
}

private fun CreatableExercise.toModel() = ExerciseModel(
    title = title ?: "",
    description = description ?: "",
    targetMuscleGroup = targetMuscleGroup?.toMutableList() ?: mutableListOf(),
    synergisticMuscleGroup = synergisticMuscleGroup?.toMutableList() ?: mutableListOf(),
    executionTechnique = executionTechnique ?: "",
)

private fun UpdatableExercise.toModel() = ExerciseModel(
    title = title ?: "",
    description = description ?: "",
    targetMuscleGroup = targetMuscleGroup?.toMutableList() ?: mutableListOf(),
    synergisticMuscleGroup = synergisticMuscleGroup?.toMutableList() ?: mutableListOf(),
    executionTechnique = executionTechnique ?: "",
    idExercise = ExerciseIdModel(id ?: "")
)

private fun CreatableWorkout.toModel() = WorkoutModel(
    workoutDate = date?.takeIf { it.isNotBlank() } ?: "",
    duration = duration?.takeIf { it > 0.0 } ?: 0.0,
    recoveryTime = recoveryTime?.takeIf { it > 0.0 } ?: 0.0,
    modificationWorkout = WorkoutModel.ModificationWorkout.valueOf(modificationWorkout?.name ?: "CLASSIC"),
    exercisesBlock = exercisesBlock.takeIf { !it.isNullOrEmpty() }?.map { it.toModel() }?.toMutableList()
        ?: mutableListOf()
)

private fun ExercisesBlock.toModel() = ExercisesBlockModel(
    exercise = exercise?.toModel() ?: ExerciseModel(),
    sets = sets.takeIf { !it.isNullOrEmpty() }?.map { it.toModel() }?.toMutableList() ?: mutableListOf()
)

private fun ResponseExercise.toModel() = ExerciseModel(
    title = title ?: "",
    description = description ?: "",
    targetMuscleGroup = targetMuscleGroup?.toMutableList() ?: mutableListOf(),
    synergisticMuscleGroup = synergisticMuscleGroup?.toMutableList() ?: mutableListOf(),
    executionTechnique = executionTechnique ?: "",
    idExercise = ExerciseIdModel(id ?: "")
)

private fun OneSet.toModel() = OneSetModel(
    performance = performance.takeIf { it.isNullOrEmpty() }?.map { it.toModel() }?.toMutableList() ?: mutableListOf(),
    status = OneSetModel.Status.valueOf(status?.value ?: "PLAN"),
    modificationExercise = OneSetModel.ModificationExercise.valueOf(modificationExercise?.value ?: "NONE")
)

private fun Performance.toModel() = PerformanceModel(
    weight = weight?.takeIf { it > 0.0 } ?: 0.0,
    measure = PerformanceModel.Measure.valueOf(measure?.name ?: "KG"),
    repetition = repetition?.takeIf { it > 0 } ?: 0
)

private fun UpdatableWorkout.toModel() = WorkoutModel(
    workoutDate = date ?: "",
    duration = duration?.takeIf { it > 0.0 } ?: 0.0,
    recoveryTime = recoveryTime?.takeIf { it > 0.0 } ?: 0.0,
    modificationWorkout = WorkoutModel.ModificationWorkout.valueOf(modificationWorkout?.name ?: "CLASSIC"),
    exercisesBlock = exercisesBlock.takeIf { !it.isNullOrEmpty() }?.map { it.toModel() }?.toMutableList()
        ?: mutableListOf(),
    idWorkout = WorkoutIdModel(id ?: "")
)
