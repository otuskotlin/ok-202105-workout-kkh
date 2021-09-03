package ru.workout.otuskotlin.workout.backend.common.context

import ru.workout.otuskotlin.workout.backend.common.models.*
import java.time.Instant

data class BeContext(
    var startTime: Instant = Instant.MIN,
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
    var status: CorStatus = CorStatus.STARTED
) {
    private fun addError(error: IError, failingsStatus: Boolean = true) = apply {
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