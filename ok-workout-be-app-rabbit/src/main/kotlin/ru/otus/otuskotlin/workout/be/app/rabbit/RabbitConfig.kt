package ru.otus.otuskotlin.workout.be.app.rabbit

import ru.otus.otuskotlin.workout.be.service.openapi.exceptions.ExerciseService
import ru.otus.otuskotlin.workout.be.service.openapi.exceptions.WorkoutService
import ru.otus.otuskotlin.workout.backend.logics.ExerciseCrud
import ru.otus.otuskotlin.workout.backend.logics.WorkoutCrud

class RabbitConfig(
    val host: String = Companion.host,
    val port: Int = Companion.port,
    val user: String = Companion.user,
    val password: String = Companion.password,
    val exerciseService: ExerciseService = ExerciseService(ExerciseCrud()),
    val workoutService: WorkoutService = WorkoutService(WorkoutCrud())
) {
    companion object {
        val host = "localhost"
        val port = 5672
        val user = "guest"
        val password = "guest"
    }
}
