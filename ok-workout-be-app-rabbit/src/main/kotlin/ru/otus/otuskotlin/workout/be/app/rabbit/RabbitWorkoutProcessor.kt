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
    private val workoutService: WorkoutService
) : RabbitProcessor(config, consumerTag) {
    private val objectMapper = ObjectMapper()
    override fun Channel.getDeliverCallback(): DeliverCallback {
        val channel = this
        return DeliverCallback { consumerTag, message ->
            runBlocking {
                try {
                    when (val query = objectMapper.readValue(message.body, BaseMessage::class.java)) {
                        is InitWorkoutRequest -> {
                            val context = BeContext(startTime = Instant.now())
                            val response = workoutService.initWorkout(context, query)
                            objectMapper.writeValueAsBytes(response)
                        }
                        is CreateWorkoutRequest -> {
                            val context = BeContext(startTime = Instant.now())
                            val response = workoutService.createWorkout(context, query)
                            objectMapper.writeValueAsBytes(response)
                        }
                        is ReadWorkoutRequest -> {
                            val context = BeContext(startTime = Instant.now())
                            val response = workoutService.readWorkout(context, query)
                            objectMapper.writeValueAsBytes(response)
                        }
                        is UpdateWorkoutRequest -> {
                            val context = BeContext(startTime = Instant.now())
                            val response = workoutService.updateWorkout(context, query)
                            objectMapper.writeValueAsBytes(response)
                        }
                        is DeleteWorkoutRequest -> {
                            val context = BeContext(startTime = Instant.now())
                            val response = workoutService.deleteWorkout(context, query)
                            objectMapper.writeValueAsBytes(response)
                        }
                        is SearchWorkoutRequest -> {
                            val context = BeContext(startTime = Instant.now())
                            val response = workoutService.searchWorkout(context, query)
                            objectMapper.writeValueAsBytes(response)
                        }
                        is ChainOfExercisesRequest -> {
                            val context = BeContext(startTime = Instant.now())
                            val response = workoutService.chainOfExercises(context, query)
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
                        objectMapper.writeValueAsBytes(workoutService.initWorkout(context, InitWorkoutRequest()))
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
        channel.exchangeDeclare(exchange, "workout")
        val queue = channel.queueDeclare().queue
        channel.queueBind(queue, exchange, keyIn)
        channel.basicConsume(queue, true, deliverCallback, cancelCallback)
    }
}