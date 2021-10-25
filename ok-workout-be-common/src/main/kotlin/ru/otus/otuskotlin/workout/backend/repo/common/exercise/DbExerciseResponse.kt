package ru.otus.otuskotlin.workout.backend.repo.common.exercise

import ru.otus.otuskotlin.workout.backend.common.models.CommonErrorModel
import ru.otus.otuskotlin.workout.backend.common.models.ExerciseModel
import ru.otus.otuskotlin.workout.backend.repo.common.IDbResponse

data class DbExerciseResponse(
    override val isSuccess: Boolean,
    override val errors: List<CommonErrorModel> = emptyList(),
    override val result: ExerciseModel?
) : IDbResponse<ExerciseModel?> {
    constructor(result: ExerciseModel) : this(true, emptyList(), result)
    constructor(e: Exception) : this(false, listOf(CommonErrorModel(e)), null)
    constructor(error: CommonErrorModel) : this(false, listOf(error), null)
}