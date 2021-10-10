package ru.workout.otuskotlin.workout.backend.common.repo.common.workout

import ru.workout.otuskotlin.workout.backend.common.models.CommonErrorModel
import ru.workout.otuskotlin.workout.backend.common.models.ExerciseModel
import ru.workout.otuskotlin.workout.backend.common.repo.common.IDbResponse

class DbWorkoutsModelResponse(
    override val isSuccess: Boolean,
    override val errors: List<CommonErrorModel>,
    override val result: List<ExerciseModel>
) : IDbResponse<List<ExerciseModel>>
