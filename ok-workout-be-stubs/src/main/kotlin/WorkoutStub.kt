import ru.otus.otuskotlin.workout.backend.common.models.*
import java.time.Instant

object WorkoutStub {
    private val workoutModelStub = WorkoutModel(
        workoutDate = Instant.parse("2021-08-23T14:00:00.0Z"),
        duration = 120.0,
        recoveryTime = 120.0,
        modificationWorkout = WorkoutModel.ModificationWorkout.CIRCUIT,
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
                ),
                modificationBlockExercises = ModificationBlockExercises.NONE
            )
        ),
        idWorkout = WorkoutIdModel("wID:0001"),
        permissions = mutableSetOf(ExercisePermissions.READ)
    )

    private val workoutModelStubTwo = WorkoutModel(
        workoutDate = Instant.parse("2021-08-23T14:00:00.0Z"),
        duration = 100.0,
        recoveryTime = 100.0,
        modificationWorkout = WorkoutModel.ModificationWorkout.CIRCUIT,
        exercisesBlock = mutableListOf(
            ExercisesBlockModel(
                ExerciseStub.getModelExerciseTwo(),
                sets = mutableListOf(
                    OneSetModel(
                        performance = mutableListOf(
                            PerformanceModel(
                                weight = 100.0,
                                repetition = 10
                            )
                        )
                    )
                ),
                modificationBlockExercises = ModificationBlockExercises.NONE
            )
        ),
        idWorkout = WorkoutIdModel("wID:0001"),
        permissions = mutableSetOf(ExercisePermissions.READ)
    )

    fun getModelWorkout(model: (WorkoutModel.() -> Unit)? = null) = workoutModelStub.also { stub ->
        model?.let { stub.apply(it) }
    }

    fun getModelWorkoutTwo() = workoutModelStubTwo

    fun getWorkouts() = mutableListOf(workoutModelStub, workoutModelStubTwo)

    fun getChainOfExercises() = workoutModelStub.exercisesBlock

}