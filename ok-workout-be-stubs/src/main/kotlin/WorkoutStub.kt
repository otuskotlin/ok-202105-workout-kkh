import ru.workout.otuskotlin.workout.backend.common.models.*
import java.time.Instant

object WorkoutStub {
    private val workoutModelStub = WorkoutModel(
        workoutDate = Instant.parse("2021-08-23T14:00:00.0Z"),
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
        ),
        idWorkout = WorkoutIdModel("wID:0001"),
        permissions = mutableSetOf(ExercisePermissions.READ)
    )

    fun getModelWorkout() = workoutModelStub
}

fun main() {
    println(Instant.now())
    val date = "2021-08-23T14:00:00.0Z"
    val inst = Instant.parse(date)
    println(inst)
}