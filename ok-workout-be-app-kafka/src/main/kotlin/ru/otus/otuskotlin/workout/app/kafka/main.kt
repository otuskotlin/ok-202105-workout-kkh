package ru.otus.otuskotlin.workout.app.kafka

fun main() {
    val config = AppKafkaConfig()
    val consumer = AppKafkaConsumer(config)
    consumer.run()
}