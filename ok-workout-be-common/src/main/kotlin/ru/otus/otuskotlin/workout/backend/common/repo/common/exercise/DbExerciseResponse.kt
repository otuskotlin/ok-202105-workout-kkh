package ru.otus.otuskotlin.workout.backend.common.repo.common.exercise

import ru.otus.otuskotlin.workout.backend.common.models.CommonErrorModel
import ru.otus.otuskotlin.workout.backend.common.models.ExerciseModel
import ru.otus.otuskotlin.workout.backend.common.repo.common.IDbResponse

data class DbExerciseResponse(
    override val isSuccess: Boolean,
    override val errors: List<CommonErrorModel> = emptyList(),
    override val result: ExerciseModel?
) : IDbResponse<ExerciseModel?>