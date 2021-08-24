package ru.otus.services

import ru.workout.otuskotlin.workout.backend.common.context.BeContext

class WorkoutService {
    fun createWorkout(beContext: BeContext): BeContext {
        return beContext.apply {
            responseWorkout = WorkoutStub.getModelWorkout()
        }
    }
    fun readWorkout(beContext: BeContext): BeContext {
        return beContext.apply {
            responseWorkout = WorkoutStub.getModelWorkout()
        }
    }
    fun updateWorkout(beContext: BeContext): BeContext {
        return beContext.apply {
            responseWorkout = WorkoutStub.getModelWorkout()
        }
    } fun deleteWorkout(beContext: BeContext): BeContext {
        return beContext.apply {
            responseWorkout = WorkoutStub.getModelWorkout()
        }
    }
    fun searchWorkout(beContext: BeContext): BeContext {
        return beContext.apply {
            responseWorkout = WorkoutStub.getModelWorkout()
        }
    }

}