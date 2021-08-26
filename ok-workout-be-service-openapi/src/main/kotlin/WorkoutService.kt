import ru.otus.otuskotlin.workout.openapi.models.*
import ru.workout.otuskotlin.workout.backend.common.context.BeContext
import ru.workout.otuskotlin.workout.backend.mapping.openapi.*

class WorkoutService {
    fun createWorkout(context: BeContext, request: CreateWorkoutRequest): CreateWorkoutResponse {
        context.setQuery(request)
        return context.toCreateWorkoutResponse()
    }

    fun readWorkout(context: BeContext, request: ReadWorkoutRequest): ReadWorkoutResponse {
        context.setQuery(request)
        return context.toReadWorkoutResponse()
    }

    fun updateWorkout(context: BeContext, request: UpdateWorkoutRequest): UpdateWorkoutResponse {
        context.setQuery(request)

        return context.toUpdateWorkoutResponse()
    }

    fun deleteWorkout(context: BeContext, request: DeleteWorkoutRequest): DeleteWorkoutResponse {
        context.setQuery(request)
        return context.toDeleteWorkoutResponse()
    }

    fun searchWorkout(context: BeContext, request: SearchWorkoutRequest): SearchWorkoutResponse {
        context.setQuery(request)
        return context.toSearchWorkoutResponse()
    }

    fun chainOfExercises(context: BeContext, request: ReadWorkoutRequest): ChainOfExercisesResponse {
        context.setQuery(request)
        return context.toChainOfExercisesResponse()
    }

    fun errorWorkout(context: BeContext, e: Throwable): BaseMessage {
        context.addError {
            from(e)
        }
        return context.toReadWorkoutResponse()
    }
}