package ru.otus.otuskotlin.workout.be.app.rabbit

import WorkoutService
import com.fasterxml.jackson.databind.ObjectMapper
import com.rabbitmq.client.CancelCallback
import com.rabbitmq.client.Channel
import com.rabbitmq.client.DeliverCallback
import kotlinx.coroutines.runBlocking
import ru.otus.otuskotlin.workout.openapi.models.*
import ru.workout.otuskotlin.workout.backend.common.context.BeContext
import ru.workout.otuskotlin.workout.backend.common.context.CorStatus
import java.time.Instant

class RabbitWorkoutProcessor(
    config: RabbitConfig,
    consumerTag: String,
    private val keyIn: String,
    private val keyOut: String,
    private val exchange: String,
    private val queue: String,
    private val workoutService: WorkoutService
) : RabbitProcessor(config, consumerTag) {
    private val objectMapper = ObjectMapper()
    override fun Channel.getDeliverCallback(): DeliverCallback {
        val channel = this
        return DeliverCallback { consumerTag, message ->
            runBlocking {
                val context = BeContext(startTime = Instant.now())
                try {
                    val query = objectMapper.safeReadValue(message.body)
                    val response = workoutService.handleRequest(context, query)
                    objectMapper.safeWriteValueAsBytes(response).also {
                        channel.basicPublish(exchange, keyOut, null, it)
                    }
                } catch (t: Throwable) {
                    context.status = CorStatus.ERROR
                    context.addError(t)
                    val response =
                        objectMapper.safeWriteValueAsBytes(workoutService.initWorkout(context, InitWorkoutRequest()))
                    channel.basicPublish(exchange, keyOut, null, response)
                }
            }
        }
    }

    override fun Channel.getCancelCallback(): CancelCallback = CancelCallback {
        println("$it was cancelled")
    }

    override fun Channel.listen(deliverCallback: DeliverCallback, cancelCallback: CancelCallback) {
        // Объявляем обменник типа "direct" (сообщения передаются в те очереди, где ключ совпадает)
        exchangeDeclare(exchange, "direct")
        // Объявляем очередь (не сохраняется при перезагрузке сервера; неэксклюзивна - доступна другим соединениям;
        // не удаляется, если не используется)
        queueDeclare(queue, false, false, false, null)
        // связываем обменник с очередью по ключу (сообщения будут поступать в данную очередь
        // с данного обменника при совпадении ключа)
        queueBind(queue, exchange, keyIn)
        // запуск консьюмера с автоотправкой подтверждение при получении сообщения
        basicConsume(queue, true, deliverCallback, cancelCallback)
        while (isOpen) {
            try {
                Thread.sleep(100)
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }
        }
        println("Channel for $consumerTag was closed.")
    }
}