package ru.otus.otuskotlin.workout.backend.repo.cassandra

import com.datastax.oss.driver.api.mapper.annotations.CqlName
import com.datastax.oss.driver.api.mapper.annotations.Entity
import com.datastax.oss.driver.api.mapper.annotations.PartitionKey
import ru.otus.otuskotlin.workout.backend.common.models.ExerciseIdModel
import ru.otus.otuskotlin.workout.backend.common.models.ExerciseModel
import ru.otus.otuskotlin.workout.backend.common.models.ExercisePermissions

@Entity
data class ExerciseCassandraDTO(
    @PartitionKey
    @CqlName(COLUMN_ID)
    val id: String?,
    @CqlName(COLUMN_TITLE)
    val title: String?,
    @CqlName(COLUMN_DESCRIPTION)
    val description: String?,
    @CqlName(COLUMN_TARGET_MUSCLE_GROUP)
    val targetMuscleGroup: MutableList<String>?,
    @CqlName(COLUMN_SYNERGISTIC_MUSCLE_GROUP)
    val synergisticMuscleGroup: MutableList<String>?,
    @CqlName(COLUMN_EXECUTION_TECHNIQUE)
    val executionTechnique: String?,
    @CqlName(COLUMN_PERMISSIONS)
    val permissions: MutableSet<ExercisePermissions>?
) {
    constructor(model: ExerciseModel) : this(
        id = model.idExercise.takeIf { it != ExerciseIdModel.NONE }?.asString(),
        title = model.title.takeIf { it.isNotBlank() },
        description = model.description.takeIf { it.isNotBlank() },
        targetMuscleGroup = model.targetMuscleGroup.takeIf { it.isNotEmpty() },
        synergisticMuscleGroup = model.synergisticMuscleGroup.takeIf { it.isNotEmpty() },
        executionTechnique = model.executionTechnique.takeIf { it.isNotBlank() },
        permissions = model.permissions.takeIf { it.isNotEmpty() }
    )

    fun toExerciseModel(): ExerciseModel =
        ExerciseModel(
            title = title ?: "",
            description = description ?: "",
            targetMuscleGroup = targetMuscleGroup ?: mutableListOf(),
            synergisticMuscleGroup = synergisticMuscleGroup ?: mutableListOf(),
            executionTechnique = executionTechnique ?: "",
            idExercise = id?.let { ExerciseIdModel(it) } ?: ExerciseIdModel.NONE,
            permissions = permissions.orEmpty().toMutableSet()
        )

    companion object {
        const val TABLE_NAME = "exercises"
        const val COLUMN_ID = "id"
        const val COLUMN_TITLE = "title"
        const val COLUMN_DESCRIPTION = "description"
        const val COLUMN_TARGET_MUSCLE_GROUP = "target_muscle_group"
        const val COLUMN_SYNERGISTIC_MUSCLE_GROUP = "synergistic_muscle_group"
        const val COLUMN_EXECUTION_TECHNIQUE = "execution_technique"
        const val COLUMN_PERMISSIONS = "permissions"
    }
}
