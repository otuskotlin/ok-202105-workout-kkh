package ru.workout.otuskotlin.workout.backend.common.context

import ru.workout.otuskotlin.workout.backend.common.models.*

data class BeContext(
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
    var errors: MutableList<IError> = mutableListOf()
)