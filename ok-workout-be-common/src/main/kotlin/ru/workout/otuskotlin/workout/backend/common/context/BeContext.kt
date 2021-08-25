package ru.workout.otuskotlin.workout.backend.common.context

import ru.workout.otuskotlin.workout.backend.common.models.*
import java.time.Instant

data class BeContext(
    var startTime: Instant = Instant.MIN,
    var requestId: String = "",
    var requestExerciseId: ExerciseIdModel = ExerciseIdModel.NONE,
    var requestExercise: ExerciseModel = ExerciseModel(),
    var responseExercise: ExerciseModel = ExerciseModel(),
    var responseExercises: MutableList<ExerciseModel> = mutableListOf(),
    var requestSearchExercise: String = "",
    var requestWorkoutId: WorkoutIdModel = WorkoutIdModel.NONE,
    var requestWorkout: WorkoutModel = WorkoutModel(),
    var responseWorkout: WorkoutModel = WorkoutModel(),
    var responseWorkouts: MutableList<WorkoutModel> = mutableListOf(),
    var requestSearchWorkout: SearchWorkoutModel = SearchWorkoutModel(),
    var errors: MutableList<IError> = mutableListOf(),
    var foundExercises: MutableList<ExerciseModel> = mutableListOf(),
    var foundWorkouts: MutableList<WorkoutModel> = mutableListOf(),
    var status: CorStatus = CorStatus.STARTED
) {
    fun addError(failingStatus: Boolean = true, lambda: CommonErrorModel.() -> Unit) = apply {
        if (failingStatus) status = CorStatus.FAILING
        errors.add(
            CommonErrorModel(
                field = "_", level = IError.Level.ERROR
            ).apply(lambda)
        )
    }
}