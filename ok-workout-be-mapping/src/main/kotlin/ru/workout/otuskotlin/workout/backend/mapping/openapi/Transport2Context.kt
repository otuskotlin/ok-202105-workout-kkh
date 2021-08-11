package ru.workout.otuskotlin.workout.backend.mapping.openapi

import ru.otus.otuskotlin.workout.openapi.models.*
import ru.workout.otuskotlin.workout.backend.common.context.BeContext
import ru.workout.otuskotlin.workout.backend.common.models.ExerciseIdModel
import ru.workout.otuskotlin.workout.backend.common.models.ExerciseModel
import java.text.SimpleDateFormat
import java.time.Instant
import java.util.*

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

fun BeContext.setQuery(query: SearchWorkoutRequest) = apply {
    requestId = query.requestId ?: ""
    requestSearchWorkout.apply {
        date = SimpleDateFormat("yyyy-MM-dd").parse(query.date) ?: Date.from(Instant.now())
        searchMuscleGroup = query.searchMuscleGroup ?: ""
        searchExercise = query.searchExercise ?: ""
    }
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