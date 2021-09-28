package ru.otus.otuskotlin.workout.be.app.rabbit

import com.rabbitmq.client.CancelCallback
import com.rabbitmq.client.Channel
import com.rabbitmq.client.ConnectionFactory
import com.rabbitmq.client.DeliverCallback
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

abstract class RabbitProcessor(
    val config: RabbitConfig,
    val consumerTag: String
) {
    suspend fun process() {
        withContext(Dispatchers.IO) {
            ConnectionFactory().apply {
                host = config.host
                port = config.port
                username = config.user
                password = config.password
            }.newConnection().use { connection ->
                connection.createChannel().use { channel ->
                    val deliverCallback = channel.getDeliverCallback()
                    val cancelCallback = channel.getCancelCallback()
                    channel.listen(deliverCallback, cancelCallback)
                }
            }
        }
    }

    protected abstract fun Channel.getDeliverCallback(): DeliverCallback
    protected abstract fun Channel.getCancelCallback(): CancelCallback
    protected abstract fun Channel.listen(deliverCallback: DeliverCallback, cancelCallback: CancelCallback)

}
