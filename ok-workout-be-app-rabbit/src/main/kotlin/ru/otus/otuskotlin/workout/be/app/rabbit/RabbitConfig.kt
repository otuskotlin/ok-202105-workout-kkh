package ru.otus.otuskotlin.workout.be.app.rabbit

class RabbitConfig(
    val host: String = Companion.host,
    val port: Int = Companion.port,
    val user: String = Companion.user,
    val password: String = Companion.password
) {
    companion object {
        val host = "localhost"
        val port = 5672
        val user = "guest"
        val password = "guest"
    }
}
