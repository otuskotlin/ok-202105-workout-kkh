package ru.otus.otuskotlin.workout.backend.common.repo.common.workout

import ru.otus.otuskotlin.workout.backend.common.models.CommonErrorModel
import ru.otus.otuskotlin.workout.backend.common.models.WorkoutModel
import ru.otus.otuskotlin.workout.backend.common.repo.common.IDbResponse

class DbWorkoutModelResponse(
    override val isSuccess: Boolean,
    override val errors: List<CommonErrorModel> = emptyList(),
    override val result: WorkoutModel?
) : IDbResponse<WorkoutModel?>
