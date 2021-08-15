package ru.otus.workoutappspring.service

import org.springframework.stereotype.Service
import ru.workout.otuskotlin.workout.backend.common.context.BeContext

@Service
class ExerciseService {
    fun create(context: BeContext): BeContext {
        val exerciseForCreation = context.requestExercise
        return context.apply {
            responseExercise = exerciseForCreation
        }
    }
}
