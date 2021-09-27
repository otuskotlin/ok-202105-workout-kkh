package ru.otus.otuskotlin.workout.be.app.rabbit

import ExerciseService
import ExerciseStub
import com.fasterxml.jackson.databind.ObjectMapper
import com.rabbitmq.client.CancelCallback
import com.rabbitmq.client.ConnectionFactory
import com.rabbitmq.client.DeliverCallback
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withTimeoutOrNull
import org.junit.Test
import org.testcontainers.containers.RabbitMQContainer
import ru.otus.otuskotlin.workout.backend.logics.ExerciseCrud
import ru.otus.otuskotlin.workout.openapi.models.BaseDebugRequest
import ru.otus.otuskotlin.workout.openapi.models.CreatableExercise
import ru.otus.otuskotlin.workout.openapi.models.CreateExerciseRequest
import ru.otus.otuskotlin.workout.openapi.models.CreateExerciseResponse
import kotlin.test.BeforeTest
import kotlin.test.assertEquals

internal class RabbitMqTest {
    companion object {
        const val keyIn = "key-in"
        const val keyOut = "key-out"
        const val exchange = "exercise-exchange"
        const val queue = "exercise-queue"
        val container by lazy {
//            Этот образ предназначен для дебагинга, он содержит панель управления на порту httpPort
//            RabbitMQContainer("rabbitmq:3-management").apply {
//            Этот образ минимальный и не содержит панель управления
            RabbitMQContainer("rabbitmq:latest").apply {
//                withExchange(exchangeIn, "fanout")
//                withQueue(queueIn, false, true, null)
//                withBinding(exchangeIn, queueIn)
//                withExposedPorts(5672, 15672)
                withUser("guest", "guest")
                start()
            }
        }

        val rabbitMqTestPort: Int by lazy {
            container.getMappedPort(5672)
        }
        val config by lazy {
            RabbitConfig(
                port = rabbitMqTestPort
            )
        }
        val crud = ExerciseCrud()
        val exerciseService = ExerciseService(crud)
        val processor by lazy {
            RabbitExerciseProcessor(
                config = config,
                keyIn = keyIn,
                keyOut = keyOut,
                exchange = exchange,
                queue = queue,
                exerciseService = exerciseService,
                consumerTag = "test-exercise-tag"
            )
        }
        val controller by lazy {
            RabbitController(processors = setOf(processor))
        }
        val mapper = ObjectMapper()
    }

    @BeforeTest
    fun tearUp() {
        controller.start()
    }

    @Test
    fun exerciseCreateTest() {
        ConnectionFactory().apply {
            host = config.host
            port = config.port
            username = "guest"
            password = "guest"
        }.newConnection().use { connection ->
            connection.createChannel().use { channel ->
                var responseJson = ""
                channel.exchangeDeclare(exchange, "direct")
                val queueOut = channel.queueDeclare().queue
                channel.queueBind(queueOut, exchange, keyOut)
                val deliverCallback = DeliverCallback { consumerTag, delivery ->
                    responseJson = String(delivery.body, Charsets.UTF_8)
                    println("Received by $consumerTag: $responseJson")
                }
                channel.basicConsume(queueOut, true, deliverCallback, CancelCallback { })
                channel.basicPublish(exchange, keyIn, null, mapper.writeValueAsBytes(exerciseCreate))

                runBlocking {
                    withTimeoutOrNull(200L) {
                        while (responseJson.isBlank()) {
                            delay(10)
                        }
                    }
                }

                println("response: $responseJson")
                val response = mapper.readValue(responseJson, CreateExerciseResponse::class.java)
                val expected = ExerciseStub.getModelExercise()
                assertEquals(expected.title, response.createdExercise?.title)
                assertEquals(expected.description, response.createdExercise?.description)
            }
        }
    }

    private val exerciseCreate = with(ExerciseStub.getModelExercise()) {
        CreateExerciseRequest(
            createExercise = CreatableExercise(
                title = title,
                description = description
            ),
            debug = BaseDebugRequest(
                mode = BaseDebugRequest.Mode.STUB,
                stubCase = BaseDebugRequest.StubCase.SUCCESS
            )
        )
    }
}