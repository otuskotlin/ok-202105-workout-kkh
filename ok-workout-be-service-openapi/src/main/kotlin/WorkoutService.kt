import ru.otus.otuskotlin.workout.backend.logics.WorkoutCrud
import ru.otus.otuskotlin.workout.openapi.models.*
import ru.workout.otuskotlin.workout.backend.common.context.BeContext
import ru.workout.otuskotlin.workout.backend.mapping.openapi.*

class WorkoutService(
    private val crud: WorkoutCrud
) {

    suspend fun initWorkout(context: BeContext, request: InitWorkoutRequest): InitWorkoutResponse {
        context.setQuery(request)
        return context.toInitWorkoutResponse()
    }

    suspend fun createWorkout(context: BeContext, request: CreateWorkoutRequest): CreateWorkoutResponse {
        crud.read(context.setQuery(request))
        context.responseWorkout = WorkoutStub.getModelWorkout()
        return context.toCreateWorkoutResponse()
    }

    suspend fun readWorkout(context: BeContext, request: ReadWorkoutRequest): ReadWorkoutResponse {
        context.setQuery(request)
        context.responseWorkout = WorkoutStub.getModelWorkout()
        return context.toReadWorkoutResponse()
    }

    suspend fun updateWorkout(context: BeContext, request: UpdateWorkoutRequest): UpdateWorkoutResponse {
        context.responseWorkout = WorkoutStub.getModelWorkout()
        context.setQuery(request)
        return context.toUpdateWorkoutResponse()
    }

    suspend fun deleteWorkout(context: BeContext, request: DeleteWorkoutRequest): DeleteWorkoutResponse {
        context.setQuery(request)
        context.responseWorkout = WorkoutStub.getModelWorkout()
        return context.toDeleteWorkoutResponse()
    }

    suspend fun searchWorkout(context: BeContext, request: SearchWorkoutRequest): SearchWorkoutResponse {
        context.setQuery(request)
        context.foundWorkouts = mutableListOf(WorkoutStub.getModelWorkout())
        return context.toSearchWorkoutResponse()
    }

    suspend fun chainOfExercises(context: BeContext, request: ReadWorkoutRequest): ChainOfExercisesResponse {
        context.setQuery(request)
        context.responseExercises = WorkoutStub.getModelWorkout().exercisesBlock
        return context.toChainOfExercisesResponse()
    }

    suspend fun errorWorkout(context: BeContext, e: Throwable): BaseMessage {
        context.addError(e)
        return context.toReadWorkoutResponse()
    }
}