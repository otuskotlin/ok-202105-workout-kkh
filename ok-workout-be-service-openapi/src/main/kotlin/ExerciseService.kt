import ru.workout.otuskotlin.workout.backend.common.context.BeContext

class ExerciseService {

    fun createExercise(beContext: BeContext): BeContext {
        return beContext.apply {
            responseExercise = ExerciseStub.getModelExercise()
        }
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
            responseExercise = ExerciseStub.getModelExercise()
        }
    }


}