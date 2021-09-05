import ru.otus.otuskotlin.workout.backend.logics.ExerciseCrud
import ru.otus.otuskotlin.workout.openapi.models.*
import ru.workout.otuskotlin.workout.backend.common.context.BeContext
import ru.workout.otuskotlin.workout.backend.mapping.openapi.*

class ExerciseService(
    private var crud: ExerciseCrud
) {

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
        context.setQuery(request)
        context.responseExercise = ExerciseStub.getModelExercise()
        return context.toUpdateExerciseResponse()
    }

    suspend fun deleteExercise(context: BeContext, request: DeleteExerciseRequest): DeleteExerciseResponse {
        crud.delete(context.setQuery(request))
        return context.toDeleteExerciseResponse()
    }

    suspend fun searchExercise(context: BeContext, request: SearchExerciseRequest): SearchExerciseResponse {
        context.setQuery(request)
        context.foundExercises = ExerciseStub.getModelExercises()
        return context.toSearchExerciseResponse()
    }

    suspend fun errorExercise(context: BeContext, e: Throwable): BaseMessage {
        context.addError(e)
        return context.toReadExerciseResponse()
    }
}