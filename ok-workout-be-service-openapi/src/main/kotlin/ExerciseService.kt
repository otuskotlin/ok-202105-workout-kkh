import ru.otus.otuskotlin.workout.openapi.models.BaseMessage
import ru.otus.otuskotlin.workout.openapi.models.CreateExerciseRequest
import ru.otus.otuskotlin.workout.openapi.models.CreateExerciseResponse
import ru.workout.otuskotlin.workout.backend.common.context.BeContext
import ru.workout.otuskotlin.workout.backend.mapping.openapi.setQuery
import ru.workout.otuskotlin.workout.backend.mapping.openapi.toCreateExerciseResponse
import ru.workout.otuskotlin.workout.backend.mapping.openapi.toReadExerciseResponse

class ExerciseService {

    fun createExercise(context: BeContext, request: CreateExerciseRequest): CreateExerciseResponse {
        context.setQuery(request)
        context.responseExercise = ExerciseStub.getModelExercise()
        return context.toCreateExerciseResponse()
    }

    fun readExercise(beContext: BeContext): BeContext {
        return beContext.apply {
            responseExercise = ExerciseStub.getModelExercise()
        }
    }

    fun updateExercise(beContext: BeContext): BeContext {
        return beContext.apply {
            responseExercise = ExerciseStub.getModelExercise()
        }
    }

    fun deleteExercise(beContext: BeContext): BeContext {
        return beContext.apply {
            responseExercise = ExerciseStub.getModelExercise()
        }
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