package ru.otus.workoutappspring.service

import org.springframework.stereotype.Service
import ru.otus.workout.stubs.Exercise
import ru.workout.otuskotlin.workout.backend.common.context.BeContext

@Service
class ExerciseService {
    fun create(context: BeContext): BeContext {
        return context.apply {
            responseExercise = Exercise.getModelExercise()
        }
    }

    fun read(context: BeContext): BeContext {
        return context.apply {
            responseExercise = Exercise.getModelExercise()
        }
    }
}
