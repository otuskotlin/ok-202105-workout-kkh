package ru.otus.otuskotlin.workout.backend.repo.cassandra

import com.datastax.oss.driver.api.core.type.DataTypes
import com.datastax.oss.driver.api.mapper.annotations.CqlName
import com.datastax.oss.driver.api.mapper.annotations.Entity
import com.datastax.oss.driver.api.mapper.annotations.PartitionKey
import com.datastax.oss.driver.api.querybuilder.SchemaBuilder
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

        fun table(
            keyspace: String,
            tableName: String
        ) = SchemaBuilder
            .createTable(keyspace, tableName)
            .ifNotExists()
            .withPartitionKey(COLUMN_ID, DataTypes.TEXT)
            .withColumn(COLUMN_TITLE, DataTypes.TEXT)
            .withColumn(COLUMN_DESCRIPTION, DataTypes.TEXT)
            .withColumn(COLUMN_TARGET_MUSCLE_GROUP, DataTypes.listOf(DataTypes.TEXT))
            .withColumn(COLUMN_SYNERGISTIC_MUSCLE_GROUP, DataTypes.listOf(DataTypes.TEXT))
            .withColumn(COLUMN_EXECUTION_TECHNIQUE, DataTypes.TEXT)
            .withColumn(COLUMN_PERMISSIONS, DataTypes.setOf(DataTypes.TEXT))
            .build()

        fun titleIndex(keyspace: String, tableName: String, locale: String = "en") =
            SchemaBuilder
                .createIndex()
                .ifNotExists()
                .usingSASI()
                .onTable(keyspace, tableName)
                .andColumn(COLUMN_TITLE)
                .withSASIOptions(mapOf("mode" to "CONTAINS", "tokenization_locale" to locale))
                .build()

        fun descriptionIndex(keyspace: String, tableName: String, locale: String = "en") =
            SchemaBuilder
                .createIndex()
                .ifNotExists()
                .usingSASI()
                .onTable(keyspace, tableName)
                .andColumn(COLUMN_DESCRIPTION)
                .withSASIOptions(mapOf("mode" to "CONTAINS", "tokenization_locale" to locale))
                .build()
    }
}
