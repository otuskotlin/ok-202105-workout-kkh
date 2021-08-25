import ru.otus.otuskotlin.workout.openapi.models.*
import ru.workout.otuskotlin.workout.backend.common.context.BeContext
import ru.workout.otuskotlin.workout.backend.mapping.openapi.*

class ExerciseService {

    fun createExercise(context: BeContext, request: CreateExerciseRequest): CreateExerciseResponse {
        context.setQuery(request)
        context.responseExercise = ExerciseStub.getModelExercise()
        return context.toCreateExerciseResponse()
    }

    fun readExercise(context: BeContext, request: ReadExerciseRequest): ReadExerciseResponse {
        context.setQuery(request)
        context.responseExercise = ExerciseStub.getModelExercise()
        return context.toReadExerciseResponse()
    }

    fun updateExercise(context: BeContext, request: UpdateExerciseRequest): UpdateExerciseResponse {
        context.setQuery(request)
        context.responseExercise = ExerciseStub.getModelExercise()
        return context.toUpdateExerciseResponse()
    }

    fun deleteExercise(context: BeContext, request: DeleteExerciseRequest): DeleteExerciseResponse {
        context.setQuery(request)
        context.responseExercise = ExerciseStub.getModelExercise()
        return context.toDeleteExerciseResponse()
    }

    fun searchExercise(beContext: BeContext): BeContext {
        return beContext.apply {
            foundExercises = ExerciseStub.getModelExercises()
        }
    }

    fun errorExercise(context: BeContext, e: Throwable): BaseMessage {
        context.addError {
            from(e)
        }
        return context.toReadExerciseResponse()
    }
}