package ru.otus.otuskotlin.workout.be.service.openapi.exceptions

import org.slf4j.event.Level
import ru.otus.otuskotlin.workout.backend.logics.ExerciseCrud
import ru.otus.otuskotlin.workout.openapi.models.*
import ru.otus.otuskotlin.workout.backend.common.context.BeContext
import ru.otus.otuskotlin.workout.backend.mapping.openapi.*
import ru.otus.otuskotlin.workout.logging.mpLogger

class ExerciseService(
    private var crud: ExerciseCrud
) : IHandlerRequests {
    private val logger = mpLogger(this::class.java)

    override suspend fun handleRequest(context: BeContext, request: BaseMessage) = try {
        when (request) {
            is InitExerciseRequest -> initExercise(context, request)
            is CreateExerciseRequest -> createExercise(context, request)
            is ReadExerciseRequest -> readExercise(context, request)
            is UpdateExerciseRequest -> updateExercise(context, request)
            is DeleteExerciseRequest -> deleteExercise(context, request)
            is SearchExerciseRequest -> searchExercise(context, request)
            else -> throw DataNotAllowedException("Request is not allowed", request)
        }
    } catch (t: Throwable) {
        handleError(context, t)
    }

    suspend fun initExercise(context: BeContext, request: InitExerciseRequest): InitExerciseResponse {
        context.setQuery(request)
        return context.handle("init-exercise", BeContext::toInitExerciseResponse)
    }

    suspend fun createExercise(context: BeContext, request: CreateExerciseRequest): CreateExerciseResponse {
        return context.handle("create-exercise", BeContext::toCreateExerciseResponse) {
            crud.create(context.setQuery(request))
        }
    }

    suspend fun readExercise(context: BeContext, request: ReadExerciseRequest): ReadExerciseResponse {
        return context.handle("read-exercise", BeContext::toReadExerciseResponse) {
            crud.read(context.setQuery(request))
        }
    }

    suspend fun updateExercise(context: BeContext, request: UpdateExerciseRequest): UpdateExerciseResponse {
        return context.handle("update-exercise", BeContext::toUpdateExerciseResponse) {
            crud.update(context.setQuery(request))
        }
    }

    suspend fun deleteExercise(context: BeContext, request: DeleteExerciseRequest): DeleteExerciseResponse {
        return context.handle("delete-exercise", BeContext::toDeleteExerciseResponse) {
            crud.delete(context.setQuery(request))
        }
    }

    suspend fun searchExercise(context: BeContext, request: SearchExerciseRequest): SearchExerciseResponse {
        return context.handle("search-exercise", BeContext::toSearchExerciseResponse) {
            crud.search(context.setQuery(request))
        }
    }

    override suspend fun handleError(context: BeContext, e: Throwable): BaseMessage {
        context.addError(e)
        return context.handle("error-exercise", BeContext::toReadExerciseResponse)
    }

    override suspend fun finish(context: BeContext) {
    }

    fun userDisconnected(context: BeContext) {
        TODO("Not yet implemented")
    }

    private suspend fun <T> BeContext.handle(
        logId: String,
        mapper: BeContext.() -> T,
        block: suspend (BeContext) -> Unit = {}
    ): T {
        logger.log(
            msg = "Request got, query = {}",
            level = Level.INFO,
            data = toLog("$logId-request-got")
        )
        block(this)
        return mapper().also {
            logger.log(
                msg = "Response ready, response = {}",
                level = Level.INFO,
                data = toLog("$logId-request-handled")
            )
        }
    }
}