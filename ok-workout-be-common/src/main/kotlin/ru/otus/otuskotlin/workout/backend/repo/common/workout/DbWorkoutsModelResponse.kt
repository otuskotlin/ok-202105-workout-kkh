package ru.otus.otuskotlin.workout.backend.repo.common.workout

import ru.otus.otuskotlin.workout.backend.common.models.CommonErrorModel
import ru.otus.otuskotlin.workout.backend.common.models.ExerciseModel
import ru.otus.otuskotlin.workout.backend.repo.common.IDbResponse

class DbWorkoutsModelResponse(
    override val isSuccess: Boolean,
    override val errors: List<CommonErrorModel>,
    override val result: List<ExerciseModel>
) : IDbResponse<List<ExerciseModel>>
