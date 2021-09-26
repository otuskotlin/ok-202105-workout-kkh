package ru.otus.otuskotlin.workout.be.app.rabbit

import com.rabbitmq.client.CancelCallback
import com.rabbitmq.client.ConnectionFactory
import com.rabbitmq.client.DeliverCallback

fun main() {
    ConnectionFactory().apply {
        host = "localhost"
        port = 5672
        username = "guest"
        password = "guest"
    }.newConnection().use { connection ->
        connection.createChannel().use { channel ->
            channel.queueDeclare("queue-in", false, false, false, null)
            val deliverCallback = DeliverCallback { consumerTag, message ->
                val messageIn = String(message.body, Charsets.UTF_8)
                channel.basicPublish(
                    "", "queue-out", null,
                    "Received $messageIn".toByteArray(Charsets.UTF_8)
                )
            }
            val cancelCallback = CancelCallback {}
            channel.basicConsume("queue-in", true, deliverCallback, cancelCallback)
            while (channel.isOpen) {

            }
        }
    }
}