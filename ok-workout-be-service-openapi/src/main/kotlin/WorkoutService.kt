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

    fun searchWorkout(beContext: BeContext): BeContext {
        return beContext.apply {
            responseWorkout = WorkoutStub.getModelWorkout()
        }
    }

    fun chainOfExercises(beContext: BeContext): BeContext {
        return beContext.apply {
            responseExercises = ExerciseStub.getModelExercises()
        }
    }

    fun errorWorkout(context: BeContext, e: Throwable): BaseMessage {
        context.addError {
            from(e)
        }
        return context.toReadWorkoutResponse()
    }
}