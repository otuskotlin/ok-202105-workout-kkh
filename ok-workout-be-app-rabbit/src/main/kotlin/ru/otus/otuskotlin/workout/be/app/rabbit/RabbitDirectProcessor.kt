package ru.otus.otuskotlin.workout.be.app.rabbit

import ExerciseService
import WorkoutService
import com.fasterxml.jackson.databind.ObjectMapper
import com.rabbitmq.client.CancelCallback
import com.rabbitmq.client.Channel
import com.rabbitmq.client.DeliverCallback
import kotlinx.coroutines.runBlocking
import ru.otus.otuskotlin.workout.openapi.models.BaseMessage
import ru.otus.otuskotlin.workout.openapi.models.CreateExerciseRequest
import ru.otus.otuskotlin.workout.openapi.models.InitExerciseRequest
import ru.workout.otuskotlin.workout.backend.common.context.BeContext
import ru.workout.otuskotlin.workout.backend.common.context.CorStatus
import java.time.Instant

class RabbitDirectProcessor(
    config: RabbitConfig,
    consumerTag: String,
    private val keyIn: String,
    private val keyOut: String,
    private val exchange: String,
    private val exerciseService: ExerciseService,
    private val workoutService: WorkoutService,
) : RabbitProcessor(config, consumerTag) {
    private val objectMapper = ObjectMapper()
    override fun Channel.getDeliverCallback(): DeliverCallback {
        val channel = this
        return DeliverCallback { consumerTag, message ->
            runBlocking {
                try {
                    when (val query = objectMapper.readValue(message.body, BaseMessage::class.java)) {
                        is CreateExerciseRequest -> {
                            val context = BeContext(startTime = Instant.now())
                            val response = exerciseService.createExercise(context, query)
                            objectMapper.writeValueAsBytes(response)
                        }
                        else -> null
                    }?.also {
                        channel.basicPublish(exchange, keyOut, null, it)
                    }
                } catch (t: Throwable) {
                    val context = BeContext(startTime = Instant.now(), status = CorStatus.ERROR)
                    context.addError(t)
                    val response =
                        objectMapper.writeValueAsBytes(exerciseService.initExercise(context, InitExerciseRequest()))
                    channel.basicPublish(exchange, keyOut, null, response)
                }
            }
        }
    }

    override fun Channel.getCancelCallback(): CancelCallback {
        TODO("Not yet implemented")
    }

    override fun Channel.listen(deliverCallback: DeliverCallback, cancelCallback: CancelCallback) {
        val channel = this
        channel.exchangeDeclare(exchange, "direct")
        val queue = channel.queueDeclare().queue
        channel.queueBind(queue, exchange, keyIn)
        channel.basicConsume(queue, true, deliverCallback, cancelCallback)
    }
}