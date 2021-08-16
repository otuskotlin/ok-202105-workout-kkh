package ru.otus.workoutappspring.service

import org.springframework.stereotype.Service
import ru.otus.workout.stubs.Exercise
import ru.otus.workoutappspring.addError
import ru.workout.otuskotlin.workout.backend.common.context.BeContext
import ru.workout.otuskotlin.workout.backend.common.models.ExerciseIdModel

@Service
class ExerciseService {
    fun createExercise(context: BeContext): BeContext {
        return context.apply {
            responseExercise = Exercise.getModelExercise()
        }
    }

    fun readExercise(context: BeContext): BeContext {
        return if (context.requestExerciseId.toString() == "eID:0001") context.apply {
            responseExercise = Exercise.getModelExercise()
        }
        else {
            context.addError {
                field = "requestExerciseId"
                message = "Not found exercise by id ${context.requestExercise.idExercise}"
            }
        }
    }

    fun updateExercise(context: BeContext) = context.apply {
        responseExercise = requestExercise
    }
}
