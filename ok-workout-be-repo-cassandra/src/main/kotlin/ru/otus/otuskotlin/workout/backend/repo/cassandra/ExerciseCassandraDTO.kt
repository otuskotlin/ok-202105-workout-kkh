package ru.otus.otuskotlin.workout.backend.repo.cassandra

import com.datastax.oss.driver.api.mapper.annotations.Entity
import com.datastax.oss.driver.api.mapper.annotations.PartitionKey
import ru.otus.otuskotlin.workout.backend.common.models.ExerciseIdModel
import ru.otus.otuskotlin.workout.backend.common.models.ExerciseModel
import ru.otus.otuskotlin.workout.backend.common.models.ExercisePermissions

@Entity
data class ExerciseCassandraDTO(
    @PartitionKey
    val id: String?,
    val title: String?,
    val description: String?,
    val targetMuscleGroup: MutableList<String>?,
    val synergisticMuscleGroup: MutableList<String>?,
    val executionTechnique: String?,
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
}
