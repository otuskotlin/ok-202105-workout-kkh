package ru.workout.otuskotlin.workout.backend.mapping.openapi

import ru.otus.otuskotlin.workout.openapi.models.*
import ru.workout.otuskotlin.workout.backend.common.context.BeContext
import ru.workout.otuskotlin.workout.backend.common.models.ExerciseIdModel
import ru.workout.otuskotlin.workout.backend.common.models.ExerciseModel

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
    // TODO: 09.08.2021 дополнить
}

private fun CreatableExercise.toModel() = ExerciseModel(
    title = title ?: "",
    description = description ?: "",
    targetMuscleGroup = targetMuscleGroup?.toMutableList() ?: mutableListOf(),
    synergisticMuscleGroup = synergisticMuscleGroup?.toMutableList() ?: mutableListOf(),
    executionTechnique = executionTechnique ?: ""
)

private fun UpdatableExercise.toModel() = ExerciseModel(
    idExercise = ExerciseIdModel(id ?: ""),
    title = title ?: "",
    description = description ?: "",
    targetMuscleGroup = targetMuscleGroup?.toMutableList() ?: mutableListOf(),
    synergisticMuscleGroup = synergisticMuscleGroup?.toMutableList() ?: mutableListOf(),
    executionTechnique = executionTechnique ?: ""
)