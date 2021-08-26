import ru.workout.otuskotlin.workout.backend.common.convertToInstant
import ru.workout.otuskotlin.workout.backend.common.models.*

object WorkoutStub {
    private val workoutModelStub = WorkoutModel(
        workoutDate = "2021-08-23 14:00 +03".convertToInstant(),
        recoveryTime = 120.0,
        exercisesBlock = mutableListOf(
            ExercisesBlockModel(
                ExerciseStub.getModelExercise(),
                sets = mutableListOf(
                    OneSetModel(
                        performance = mutableListOf(
                            PerformanceModel(
                                weight = 100.0,
                                repetition = 10
                            )
                        )
                    )
                )
            )
        )
    )

    fun getModelWorkout() = workoutModelStub
}