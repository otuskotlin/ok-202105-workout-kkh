package ru.otus.otuskotlin.workout.be.repo.inmemory.models

import ru.otus.otuskotlin.workout.backend.common.models.AuthorIdModel
import ru.otus.otuskotlin.workout.backend.common.models.ExerciseIdModel
import ru.otus.otuskotlin.workout.backend.common.models.ExerciseModel
import java.io.Serializable

data class ExerciseRow(
    val title: String? = null,
    val description: String? = null,
    val authorId: String? = null,
    val targetMuscleGroup: MutableList<String>? = null,
    val synergisticMuscleGroup: MutableList<String>? = null,
    val executionTechnique: String? = null,
    val idExercise: String? = null
) : Serializable {
    constructor(internal: ExerciseModel) : this(
        title = internal.title.takeIf { it.isNotBlank() },
        description = internal.description.takeIf { it.isNotBlank() },
        authorId = internal.authorId.takeIf { it != AuthorIdModel.NONE }?.asString(),
        targetMuscleGroup = internal.targetMuscleGroup.takeIf { it.isNotEmpty() },
        synergisticMuscleGroup = internal.synergisticMuscleGroup.takeIf { it.isNotEmpty() },
        executionTechnique = internal.executionTechnique.takeIf { it.isNotBlank() },
        idExercise = internal.idExercise.takeIf { it != ExerciseIdModel.NONE }?.asString()
    )

    fun toInternal(): ExerciseModel = ExerciseModel(
        title = title ?: "",
        description = description ?: "",
        authorId = authorId?.let { AuthorIdModel(it) } ?: AuthorIdModel.NONE,
        targetMuscleGroup = targetMuscleGroup ?: mutableListOf(),
        synergisticMuscleGroup = synergisticMuscleGroup ?: mutableListOf(),
        executionTechnique = executionTechnique ?: "",
        idExercise = idExercise?.let { ExerciseIdModel(it) } ?: ExerciseIdModel.NONE
    )
}