package ru.otus.otuskotlin.workout.app.kafka

fun main() {
    val config = AppKafkaConfig()
    val application = KafkaApplication(config)
    application.run()
}