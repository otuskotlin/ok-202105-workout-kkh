import ru.workout.otuskotlin.workout.backend.common.models.ExerciseIdModel
import ru.workout.otuskotlin.workout.backend.common.models.ExerciseModel

object ExerciseStub {
    private val exerciseStubReady = ExerciseModel(
        title = "Приседания со штангой",
        description = "",
        targetMuscleGroup = mutableListOf("Квадрицепсы"),
        synergisticMuscleGroup = mutableListOf(
            "Большие ягодичные",
            "Приводящие бедра",
            "Камбаловидные"
        ),
        executionTechnique = "Выполняющий упражнение приседает и затем встаёт, возвращаясь в положение стоя",
        idExercise = ExerciseIdModel(id = "eID:0001")
    )

    fun getModelExercise() = exerciseStubReady
}