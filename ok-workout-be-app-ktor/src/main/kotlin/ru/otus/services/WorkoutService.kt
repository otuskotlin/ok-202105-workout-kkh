package ru.otus.services

import ru.workout.otuskotlin.workout.backend.common.context.BeContext

class WorkoutService {
    fun createWorkout(beContext: BeContext): BeContext {
        return beContext.apply {
            responseWorkout = WorkoutStub.getModelWorkout()
        }
    }
}