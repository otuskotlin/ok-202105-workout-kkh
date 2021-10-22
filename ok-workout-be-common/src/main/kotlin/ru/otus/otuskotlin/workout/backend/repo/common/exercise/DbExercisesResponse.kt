package ru.otus.otuskotlin.workout.backend.repo.common.exercise

import ru.otus.otuskotlin.workout.backend.common.models.CommonErrorModel
import ru.otus.otuskotlin.workout.backend.common.models.ExerciseModel
import ru.otus.otuskotlin.workout.backend.repo.common.IDbResponse

class DbExercisesResponse(
    override val isSuccess: Boolean,
    override val errors: List<CommonErrorModel> = emptyList(),
    override val result: List<ExerciseModel>
) : IDbResponse<List<ExerciseModel>>
