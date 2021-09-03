import ru.otus.otuskotlin.workout.openapi.models.*
import ru.workout.otuskotlin.workout.backend.common.context.BeContext
import ru.workout.otuskotlin.workout.backend.mapping.openapi.*

class ExerciseService {

    fun initExercise(context: BeContext, request: InitExerciseRequest): InitExerciseResponse {
        context.setQuery(request)
        return context.toInitExerciseResponse()
    }

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

    fun searchExercise(context: BeContext, request: SearchExerciseRequest): SearchExerciseResponse {
        context.setQuery(request)
        context.foundExercises = ExerciseStub.getModelExercises()
        return context.toSearchExerciseResponse()
    }

    fun errorExercise(context: BeContext, e: Throwable): BaseMessage {
        context.addError(e)
        return context.toReadExerciseResponse()
    }
}