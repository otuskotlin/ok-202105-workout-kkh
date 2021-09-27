package ru.otus.otuskotlin.workout.be.app.rabbit

fun main() {
    val config = RabbitConfig()
    val exerciseProcessor by lazy {
        RabbitExerciseProcessor(
            config = config,
            consumerTag = "test_exercise-tag",
            keyIn = "exercise-in",
            keyOut = "exercise-out",
            exchange = "exercise-exchange",
            exerciseService = config.exerciseService
        )
    }
}