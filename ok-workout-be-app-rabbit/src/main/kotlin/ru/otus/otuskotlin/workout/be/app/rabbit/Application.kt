package ru.otus.otuskotlin.workout.be.app.rabbit

fun main() {
    val config = RabbitConfig()
    val exerciseProcessor by lazy {
        RabbitExerciseProcessor(
            config = config,
            consumerTag = "test-exercise-tag",
            keyIn = "exercise-in",
            keyOut = "exercise-out",
            queue = "exercise-queue",
            exchange = "exercise-exchange",
            exerciseService = config.exerciseService
        )
    }
    val workoutProcessor by lazy {
        RabbitWorkoutProcessor(
            config = config,
            consumerTag = "test-workout-tag",
            keyIn = "workout-in",
            keyOut = "workout-out",
            exchange = "workout-exchange",
            queue = "workout-queue",
            workoutService = config.workoutService
        )
    }
    val controller by lazy {
        RabbitController(
            processors = setOf(
                exerciseProcessor,
                workoutProcessor
            )
        )
    }
    controller.start()
}