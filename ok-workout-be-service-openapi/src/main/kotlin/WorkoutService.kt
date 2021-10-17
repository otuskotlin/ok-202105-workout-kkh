import exceptions.DataNotAllowedException
import ru.otus.otuskotlin.workout.backend.logics.WorkoutCrud
import ru.otus.otuskotlin.workout.openapi.models.*
import ru.otus.otuskotlin.workout.backend.common.context.BeContext
import ru.otus.otuskotlin.workout.backend.mapping.openapi.*

class WorkoutService(
    private val crud: WorkoutCrud
) : IHandlerRequests {

    override suspend fun handleRequest(context: BeContext, request: BaseMessage): BaseMessage = try {
        when (request) {
            is InitWorkoutRequest -> initWorkout(context, request)
            is CreateWorkoutRequest -> createWorkout(context, request)
            is ReadWorkoutRequest -> readWorkout(context, request)
            is UpdateWorkoutRequest -> updateWorkout(context, request)
            is DeleteWorkoutRequest -> deleteWorkout(context, request)
            is SearchWorkoutRequest -> searchWorkout(context, request)
            is ChainOfExercisesRequest -> chainOfExercises(context, request) //
            else -> throw DataNotAllowedException("Request is not allowed", request)
        }
    } catch (e: Throwable) {
        handleError(context, e)
    }

    suspend fun initWorkout(context: BeContext, request: InitWorkoutRequest): InitWorkoutResponse {
        context.setQuery(request)
        return context.toInitWorkoutResponse()
    }

    suspend fun createWorkout(context: BeContext, request: CreateWorkoutRequest): CreateWorkoutResponse {
        crud.create(context.setQuery(request))
        return context.toCreateWorkoutResponse()
    }

    suspend fun readWorkout(context: BeContext, request: ReadWorkoutRequest): ReadWorkoutResponse {
        crud.read(context.setQuery(request))
        return context.toReadWorkoutResponse()
    }

    suspend fun updateWorkout(context: BeContext, request: UpdateWorkoutRequest): UpdateWorkoutResponse {
        crud.update(context.setQuery(request))
        return context.toUpdateWorkoutResponse()
    }

    suspend fun deleteWorkout(context: BeContext, request: DeleteWorkoutRequest): DeleteWorkoutResponse {
        crud.delete(context.setQuery(request))
        return context.toDeleteWorkoutResponse()
    }

    suspend fun searchWorkout(context: BeContext, request: SearchWorkoutRequest): SearchWorkoutResponse {
        crud.search(context.setQuery(request))
        return context.toSearchWorkoutResponse()
    }

    suspend fun chainOfExercises(context: BeContext, request: ChainOfExercisesRequest): ChainOfExercisesResponse {
        crud.chainOfExercises(context.setQuery(request))
        return context.toChainOfExercisesResponse()
    }

    override suspend fun handleError(context: BeContext, e: Throwable): BaseMessage {
        context.addError(e)
        return context.toReadWorkoutResponse()
    }

    override suspend fun finish(context: BeContext) {
    }

    fun userDisconnected(context: BeContext) {}
}