import ru.otus.otuskotlin.workout.openapi.models.*
import ru.workout.otuskotlin.workout.backend.common.context.BeContext
import ru.workout.otuskotlin.workout.backend.mapping.openapi.*

class WorkoutService {
    fun createWorkout(context: BeContext, request: CreateWorkoutRequest): CreateWorkoutResponse {
        context.setQuery(request)
        context.responseWorkout = WorkoutStub.getModelWorkout()
        return context.toCreateWorkoutResponse()
    }

    fun readWorkout(context: BeContext, request: ReadWorkoutRequest): ReadWorkoutResponse {
        context.setQuery(request)
        context.responseWorkout = WorkoutStub.getModelWorkout()
        return context.toReadWorkoutResponse()
    }

    fun updateWorkout(context: BeContext, request: UpdateWorkoutRequest): UpdateWorkoutResponse {
        context.responseWorkout = WorkoutStub.getModelWorkout()
        context.setQuery(request)
        return context.toUpdateWorkoutResponse()
    }

    fun deleteWorkout(context: BeContext, request: DeleteWorkoutRequest): DeleteWorkoutResponse {
        context.setQuery(request)
        context.responseWorkout = WorkoutStub.getModelWorkout()
        return context.toDeleteWorkoutResponse()
    }

    fun searchWorkout(context: BeContext, request: SearchWorkoutRequest): SearchWorkoutResponse {
        context.setQuery(request)
        context.responseWorkout = WorkoutStub.getModelWorkout()
        return context.toSearchWorkoutResponse()
    }

    fun chainOfExercises(context: BeContext, request: ReadWorkoutRequest): ChainOfExercisesResponse {
        context.setQuery(request)
        context.responseExercises = WorkoutStub.getModelWorkout().exercisesBlock
        return context.toChainOfExercisesResponse()
    }

    fun errorWorkout(context: BeContext, e: Throwable): BaseMessage {
        context.addError {
            from(e)
        }
        return context.toReadWorkoutResponse()
    }
}