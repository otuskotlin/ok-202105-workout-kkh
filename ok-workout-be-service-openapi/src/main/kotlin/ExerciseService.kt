import exceptions.DataNotAllowedException
import ru.otus.otuskotlin.workout.backend.logics.ExerciseCrud
import ru.otus.otuskotlin.workout.openapi.models.*
import ru.otus.otuskotlin.workout.backend.common.context.BeContext
import ru.otus.otuskotlin.workout.backend.mapping.openapi.*

class ExerciseService(
    private var crud: ExerciseCrud
) : IHandlerRequests {

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

    fun initExercise(context: BeContext, request: InitExerciseRequest): InitExerciseResponse {
        context.setQuery(request)
        return context.toInitExerciseResponse()
    }

    suspend fun createExercise(context: BeContext, request: CreateExerciseRequest): CreateExerciseResponse {
        crud.create(context.setQuery(request))
        return context.toCreateExerciseResponse()
    }

    suspend fun readExercise(context: BeContext, request: ReadExerciseRequest): ReadExerciseResponse {
        crud.read(context.setQuery(request))
        return context.toReadExerciseResponse()
    }

    suspend fun updateExercise(context: BeContext, request: UpdateExerciseRequest): UpdateExerciseResponse {
        crud.update(context.setQuery(request))
        return context.toUpdateExerciseResponse()
    }

    suspend fun deleteExercise(context: BeContext, request: DeleteExerciseRequest): DeleteExerciseResponse {
        crud.delete(context.setQuery(request))
        return context.toDeleteExerciseResponse()
    }

    suspend fun searchExercise(context: BeContext, request: SearchExerciseRequest): SearchExerciseResponse {
        crud.search(context.setQuery(request))
        return context.toSearchExerciseResponse()
    }

    override suspend fun handleError(context: BeContext, e: Throwable): BaseMessage {
        context.addError(e)
        return context.toReadExerciseResponse()
    }

    override suspend fun finish(context: BeContext) {
    }

    fun userDisconnected(context: BeContext) {
        TODO("Not yet implemented")
    }
}