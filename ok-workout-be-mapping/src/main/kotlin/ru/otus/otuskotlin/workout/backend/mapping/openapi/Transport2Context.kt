package ru.otus.otuskotlin.workout.backend.mapping.openapi

import ru.otus.otuskotlin.workout.openapi.models.*
import ru.otus.otuskotlin.workout.openapi.models.Performance
import ru.otus.otuskotlin.workout.backend.common.context.BeContext
import ru.otus.otuskotlin.workout.backend.common.models.*
import java.time.Instant

fun BeContext.setQuery(query: InitExerciseRequest) = apply {
    operation = BeContext.MpOperations.INIT
    requestId = query.requestId ?: ""
}

fun BeContext.setQuery(query: CreateExerciseRequest) = apply {
    operation = BeContext.MpOperations.CREATE
    requestId = query.requestId ?: ""
    requestExercise = query.createExercise?.toModel() ?: ExerciseModel()
    workMode = query.debug?.mode.toModel()
    stubCase = query.debug?.stubCase.toModel()
}

fun BeContext.setQuery(query: ReadExerciseRequest) = apply {
    operation = BeContext.MpOperations.READ
    requestId = query.requestId ?: ""
    requestExerciseId = ExerciseIdModel(query.readExerciseId ?: "")
    workMode = query.debug?.mode.toModel()
    stubCase = query.debug?.stubCase.toModel()
}

fun BeContext.setQuery(query: UpdateExerciseRequest) = apply {
    operation = BeContext.MpOperations.UPDATE
    requestId = query.requestId ?: ""
    requestExercise = query.updateExercise?.toModel() ?: ExerciseModel()
    workMode = query.debug?.mode.toModel()
    stubCase = query.debug?.stubCase.toModel()
}

fun BeContext.setQuery(query: DeleteExerciseRequest) = apply {
    operation = BeContext.MpOperations.DELETE
    requestId = query.requestId ?: ""
    requestExerciseId = ExerciseIdModel(query.deleteExerciseId ?: "")
    workMode = query.debug?.mode.toModel()
    stubCase = query.debug?.stubCase.toModel()
}

fun BeContext.setQuery(query: SearchExerciseRequest) = apply {
    operation = BeContext.MpOperations.SEARCH
    requestId = query.requestId ?: ""
    requestExerciseFilter = query.search?.let {
        MpExerciseSearchFilter(searchStr = it)
    } ?: MpExerciseSearchFilter()
    workMode = query.debug?.mode.toModel()
    stubCase = query.debug?.stubCase.toModel()
}

fun BeContext.setQuery(query: InitWorkoutRequest) = apply {
    operation = BeContext.MpOperations.INIT
    requestId = query.requestId ?: ""
}

fun BeContext.setQuery(query: CreateWorkoutRequest) = apply {
    operation = BeContext.MpOperations.CREATE
    requestId = query.requestId ?: ""
    requestWorkout = query.createWorkout?.toModel() ?: WorkoutModel()
    workMode = query.debug?.mode.toModel()
    stubCase = query.debug?.stubCase.toModel()
}

fun BeContext.setQuery(query: ReadWorkoutRequest) = apply {
    operation = BeContext.MpOperations.READ
    requestId = query.requestId ?: ""
    requestWorkoutId = WorkoutIdModel(query.readWorkoutId ?: "")
    workMode = query.debug?.mode.toModel()
    stubCase = query.debug?.stubCase.toModel()
}

fun BeContext.setQuery(query: UpdateWorkoutRequest) = apply {
    operation = BeContext.MpOperations.UPDATE
    requestId = query.requestId ?: ""
    requestWorkout = query.updateWorkout?.toModel() ?: WorkoutModel()
    workMode = query.debug?.mode.toModel()
    stubCase = query.debug?.stubCase.toModel()
}

fun BeContext.setQuery(query: DeleteWorkoutRequest) = apply {
    operation = BeContext.MpOperations.DELETE
    requestId = query.requestId ?: ""
    requestWorkoutId = WorkoutIdModel(query.deleteWorkoutId ?: "")
    workMode = query.debug?.mode.toModel()
    stubCase = query.debug?.stubCase.toModel()
}

fun BeContext.setQuery(query: SearchWorkoutRequest) = apply {
    operation = BeContext.MpOperations.SEARCH
    requestId = query.requestId ?: ""
    requestSearchWorkout.apply {
        workoutDate = Instant.parse(query.date) ?: Instant.now()
        searchMuscleGroup = query.searchMuscleGroup ?: ""
        searchExercise = query.searchExercise ?: ""
    }
    workMode = query.debug?.mode.toModel()
    stubCase = query.debug?.stubCase.toModel()
}

fun BeContext.setQuery(query: ChainOfExercisesRequest) = apply {
    operation = BeContext.MpOperations.CHAIN_OF_EXERCISES
    requestId = query.requestId ?: ""
    requestWorkoutId = WorkoutIdModel(query.readWorkoutId ?: "")
    workMode = query.debug?.mode.toModel()
    stubCase = query.debug?.stubCase.toModel()
}

private fun CreatableExercise.toModel() = ExerciseModel(
    title = title ?: "",
    description = description ?: "",
    authorId = AuthorIdModel(authorId ?: ""),
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
    idExercise = ExerciseIdModel(id ?: ""),
)

private fun CreatableWorkout.toModel() = WorkoutModel(
    workoutDate = Instant.parse(date) ?: Instant.now(),
    duration = duration?.takeIf { it > 0.0 } ?: 0.0,
    recoveryTime = recoveryTime?.takeIf { it > 0.0 } ?: 0.0,
    modificationWorkout = WorkoutModel.ModificationWorkout.valueOf(modificationWorkout?.name ?: "CLASSIC"),
    exercisesBlock = exercisesBlock.takeIf { !it.isNullOrEmpty() }?.map { it.toModel() }?.toMutableList()
        ?: mutableListOf(),
)

private fun ExercisesBlock.toModel() = ExercisesBlockModel(
    exercise = exercise?.toModel() ?: ExerciseModel(),
    sets = sets.takeIf { !it.isNullOrEmpty() }?.map { it.toModel() }?.toMutableList() ?: mutableListOf(),
    modificationBlockExercises = ModificationBlockExercises.valueOf(modificationBlockExercises?.name ?: "NONE")
)

private fun ResponseExercise.toModel() = ExerciseModel(
    title = title ?: "",
    description = description ?: "",
    authorId = AuthorIdModel(authorId ?: ""),
    targetMuscleGroup = targetMuscleGroup?.toMutableList() ?: mutableListOf(),
    synergisticMuscleGroup = synergisticMuscleGroup?.toMutableList() ?: mutableListOf(),
    executionTechnique = executionTechnique ?: "",
    idExercise = ExerciseIdModel(id ?: ""),
    permissions = permissions?.map { ExercisePermissions.valueOf(it.name) }?.toMutableSet() ?: mutableSetOf()
)

private fun OneSet.toModel() = OneSetModel(
    performance = performance.takeIf { it?.isNotEmpty() ?: false }?.map { it.toModel() }?.toMutableList()
        ?: mutableListOf(),
    status = OneSetModel.Status.valueOf(status?.value ?: "PLAN"),
    modificationExercise = OneSetModel.ModificationExercise.valueOf(modificationExercise?.value ?: "NONE")
)

private fun Performance.toModel() = PerformanceModel(
    weight = weight?.takeIf { it > 0.0 } ?: 0.0,
    measure = PerformanceModel.Measure.valueOf(measure?.name ?: "KG"),
    repetition = repetition?.takeIf { it > 0 } ?: 0
)

private fun UpdatableWorkout.toModel() = WorkoutModel(
    workoutDate = Instant.parse(date) ?: Instant.now(),
    duration = duration?.takeIf { it > 0.0 } ?: 0.0,
    recoveryTime = recoveryTime?.takeIf { it > 0.0 } ?: 0.0,
    modificationWorkout = WorkoutModel.ModificationWorkout.valueOf(modificationWorkout?.name ?: "CLASSIC"),
    exercisesBlock = exercisesBlock.takeIf { !it.isNullOrEmpty() }?.map { it.toModel() }?.toMutableList()
        ?: mutableListOf(),
    idWorkout = WorkoutIdModel(id ?: "")
)

private fun BaseDebugRequest.StubCase?.toModel(): MpStubCases = when (this) {
    BaseDebugRequest.StubCase.SUCCESS -> MpStubCases.SUCCESS
    BaseDebugRequest.StubCase.DATABASE_ERROR -> MpStubCases.DATABASE_ERROR
    null -> MpStubCases.NONE
}

private fun BaseDebugRequest.Mode?.toModel() = when (this) {
    BaseDebugRequest.Mode.STUB -> WorkMode.STUB
    BaseDebugRequest.Mode.TEST -> WorkMode.TEST
    BaseDebugRequest.Mode.PROD -> WorkMode.PROD
    null -> WorkMode.PROD
}
