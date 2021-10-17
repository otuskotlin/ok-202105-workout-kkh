import ru.otus.otuskotlin.workout.backend.common.models.ExerciseIdModel
import ru.otus.otuskotlin.workout.backend.common.models.ExerciseModel
import ru.otus.otuskotlin.workout.backend.common.models.ExercisePermissions

object ExerciseStub {
    private val squatsStub = ExerciseModel(
        title = "Приседания со штангой",
        description = "Базовое упражнение",
        targetMuscleGroup = mutableListOf("Квадрицепсы"),
        synergisticMuscleGroup = mutableListOf(
            "Большие ягодичные",
            "Приводящие бедра",
            "Камбаловидные"
        ),
        executionTechnique = "Выполняющий упражнение приседает и затем встаёт, возвращаясь в положение стоя",
        idExercise = ExerciseIdModel(id = "eID:0001"),
        permissions = mutableSetOf(ExercisePermissions.READ)
    )

    private val pullUpsStub = ExerciseModel(
        title = "Подтягивания",
        description = "Базовое упражнение",
        targetMuscleGroup = mutableListOf("Широчайшие мышцы спины"),
        synergisticMuscleGroup = mutableListOf("Бицепс"),
        executionTechnique = "Подтягивание выполняется на перекладине",
        idExercise = ExerciseIdModel(id = "eID:0002"),
        permissions = mutableSetOf(ExercisePermissions.READ)
    )

    fun getModelExercise() = squatsStub
    fun getModelExerciseTwo() = pullUpsStub

    fun getModelExercise(model: (ExerciseModel.() -> Unit)? = null) = squatsStub.also { stub ->
        model?.let { stub.apply(it) }
    }

    fun getModelExercises() = mutableListOf(squatsStub, pullUpsStub)


}