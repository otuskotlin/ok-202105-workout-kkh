package ru.otus.otuskotlin.workout.backend.common.context

import ru.otus.otuskotlin.workout.backend.common.models.*
import ru.otus.otuskotlin.workout.backend.repo.common.exercise.IRepoExercise
import java.time.Instant

data class BeContext(
    var startTime: Instant = Instant.MIN,
    var operation: MpOperations = MpOperations.NONE,
    var stubCase: MpStubCases = MpStubCases.NONE,
    var workMode: WorkMode = WorkMode.PROD,

    val userSession: IUserSession<*> = IUserSession.Companion.EmptySession,
    var principal: MpPrincipalModel = MpPrincipalModel.NONE,
    val chainPermissions: MutableSet<MpUserPermissions> = mutableSetOf(),

    var config: ContextConfig = ContextConfig(),
    var exerciseRepo: IRepoExercise = IRepoExercise.NONE,

    var requestId: String = "",
    var requestExerciseId: ExerciseIdModel = ExerciseIdModel.NONE,
    var requestExercise: ExerciseModel = ExerciseModel(),
    var responseExercise: ExerciseModel = ExerciseModel(),
    var responseExercises: MutableList<ExercisesBlockModel> = mutableListOf(),
    var requestSearchExercise: String = "",
    var requestWorkoutId: WorkoutIdModel = WorkoutIdModel.NONE,
    var requestWorkout: WorkoutModel = WorkoutModel(),
    var responseWorkout: WorkoutModel = WorkoutModel(),
    var requestSearchWorkout: SearchWorkoutModel = SearchWorkoutModel(),
    var errors: MutableList<IError> = mutableListOf(),
    var foundExercises: MutableList<ExerciseModel> = mutableListOf(),
    var foundWorkouts: MutableList<WorkoutModel> = mutableListOf(),
    var status: CorStatus = CorStatus.NONE
) {

    enum class MpOperations {
        NONE,
        INIT,
        CREATE,
        READ,
        UPDATE,
        DELETE,
        SEARCH,
        CHAIN_OF_EXERCISES
    }

    fun addError(error: IError, failingsStatus: Boolean = true) = apply {
        if (failingsStatus) status = CorStatus.FAILING
        errors.add(error)
    }

    fun addError(
        e: Throwable,
        level: IError.Level = IError.Level.ERROR,
        field: String = "",
        failingsStatus: Boolean = true
    ) {
        addError(CommonErrorModel(e, field = field, level = level), failingsStatus)
    }
}