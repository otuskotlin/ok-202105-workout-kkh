package ru.otus.otuskotlin.workout.be.service.openapi.exceptions

import ru.otus.otuskotlin.workout.openapi.models.BaseMessage
import ru.otus.otuskotlin.workout.backend.common.context.BeContext

interface IHandlerRequests {
    suspend fun handleRequest(context: BeContext, request: BaseMessage): BaseMessage
    suspend fun handleError(context: BeContext, e: Throwable): BaseMessage
    suspend fun finish(context: BeContext)
}