package ru.otus.otuskotlin.workout.backend.common.repo.common

import ru.otus.otuskotlin.workout.backend.common.models.CommonErrorModel

interface IDbResponse<T> {
    val isSuccess: Boolean
    val errors: List<CommonErrorModel>
    val result: T
}