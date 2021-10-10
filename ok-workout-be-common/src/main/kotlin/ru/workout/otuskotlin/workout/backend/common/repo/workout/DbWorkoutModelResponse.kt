package ru.workout.otuskotlin.workout.backend.common.repo.workout

import ru.workout.otuskotlin.workout.backend.common.models.CommonErrorModel
import ru.workout.otuskotlin.workout.backend.common.models.WorkoutModel
import ru.workout.otuskotlin.workout.backend.common.repo.IDbResponse

class DbWorkoutModelResponse(
    override val isSuccess: Boolean,
    override val errors: List<CommonErrorModel> = emptyList(),
    override val result: WorkoutModel?
) : IDbResponse<WorkoutModel?>
