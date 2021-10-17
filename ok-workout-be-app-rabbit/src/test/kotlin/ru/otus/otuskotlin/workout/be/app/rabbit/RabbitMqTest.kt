package ru.otus.otuskotlin.workout.be.app.rabbit

import ru.otus.otuskotlin.workout.be.service.openapi.exceptions.ExerciseService
import ExerciseStub
import ru.otus.otuskotlin.workout.be.service.openapi.exceptions.WorkoutService
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
import ru.otus.otuskotlin.workout.backend.logics.WorkoutCrud
import ru.otus.otuskotlin.workout.openapi.models.*
import kotlin.test.BeforeTest
import kotlin.test.assertEquals

internal class RabbitMqTest {
    companion object {
        const val exerciseKeyIn = "exercise-key-in"
        const val exerciseKeyOut = "exercise-key-out"
        const val workoutKeyIn = "workout-key-in"
        const val workoutKeyOut = "workout-key-out"
        const val exerciseExchange = "exercise-exchange"
        const val workoutExchange = "workout-exchange"
        private const val exerciseQueue = "exercise-queue"
        private const val workoutQueue = "workout-queue"
        private val container by lazy {
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

        private val rabbitMqTestPort: Int by lazy {
            container.getMappedPort(5672)
        }
        val config by lazy {
            RabbitConfig(
                port = rabbitMqTestPort
            )
        }
        private val exerciseService = ExerciseService(ExerciseCrud())
        private val workoutService = WorkoutService(WorkoutCrud())
        private val exerciseProcessor by lazy {
            RabbitExerciseProcessor(
                config = config,
                consumerTag = "test-exercise-tag",
                keyIn = exerciseKeyIn,
                keyOut = exerciseKeyOut,
                exchange = exerciseExchange,
                queue = exerciseQueue,
                exerciseService = exerciseService
            )
        }
        private val workoutProcessor by lazy {
            RabbitWorkoutProcessor(
                config = config,
                consumerTag = "test-workout-tag",
                keyIn = workoutKeyIn,
                keyOut = workoutKeyOut,
                exchange = workoutExchange,
                queue = workoutQueue,
                workoutService = workoutService

            )
        }

        val controller by lazy {
            RabbitController(processors = setOf(exerciseProcessor, workoutProcessor))
        }
        val objectMapper = ObjectMapper()
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
                channel.exchangeDeclare(exerciseExchange, "direct")
                val queueOut = channel.queueDeclare().queue
                channel.queueBind(queueOut, exerciseExchange, exerciseKeyOut)
                val deliverCallback = DeliverCallback { consumerTag, delivery ->
                    responseJson = String(delivery.body, Charsets.UTF_8)
                    println("Received by $consumerTag: $responseJson")
                }
                channel.basicConsume(queueOut, true, deliverCallback, CancelCallback { })
                channel.basicPublish(
                    exerciseExchange,
                    exerciseKeyIn,
                    null,
                    objectMapper.writeValueAsBytes(exerciseCreate)
                )

                runBlocking {
                    withTimeoutOrNull(200L) {
                        while (responseJson.isBlank()) {
                            delay(10)
                        }
                    }
                }

                println("response: $responseJson")
                val response = objectMapper.readValue(responseJson, CreateExerciseResponse::class.java)
                val expected = ExerciseStub.getModelExercise()
                assertEquals(expected.title, response.createdExercise?.title)
                assertEquals(expected.description, response.createdExercise?.description)
            }
        }
    }

    @Test
    fun workoutCreateTest() {
        ConnectionFactory().apply {
            host = config.host
            port = config.port
            username = "guest"
            password = "guest"
        }.newConnection().use { connection ->
            connection.createChannel().use { channel ->
                var responseJson = ""
                channel.exchangeDeclare(workoutExchange, "direct")
                val queueOut = channel.queueDeclare().queue
                channel.queueBind(queueOut, workoutExchange, workoutKeyOut)
                val deliverCallback = DeliverCallback { consumerTag, delivery ->
                    responseJson = String(delivery.body, Charsets.UTF_8)
                    println("Received by $consumerTag: $responseJson")
                }
                channel.basicConsume(queueOut, true, deliverCallback, CancelCallback { })
                channel.basicPublish(
                    workoutExchange, workoutKeyIn, null, objectMapper.writeValueAsBytes(workoutCreate)
                )

                runBlocking {
                    withTimeoutOrNull(200L) {
                        while (responseJson.isBlank()) {
                            delay(10)
                        }
                    }
                }
                println("response: $responseJson")
                val response = objectMapper.readValue(responseJson, CreateWorkoutResponse::class.java)
                val expected = WorkoutStub.getModelWorkout()
                assertEquals(expected.workoutDate.toString(), response.createdWorkout?.date)
                assertEquals(expected.duration, response.createdWorkout?.duration)
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

    private val workoutCreate = with(WorkoutStub.getModelWorkout()) {
        CreateWorkoutRequest(
            createWorkout = CreatableWorkout(
                date = "2021-08-23T14:00:00.0Z",
                duration = 120.0,
                recoveryTime = 120.0,
                modificationWorkout = CreatableWorkout.ModificationWorkout.CLASSIC,
                exercisesBlock = listOf(
                    ExercisesBlock(
                        exercise = ResponseExercise(
                            title = "Приседания со штангой",
                            description = "Базовое упражнение",
                            targetMuscleGroup = mutableListOf("Квадрицепсы"),
                            synergisticMuscleGroup = mutableListOf(
                                "Большие ягодичные",
                                "Приводящие бедра",
                                "Камбаловидные"
                            ),
                            executionTechnique = "Выполняющий упражнение приседает и затем встаёт, возвращаясь в положение стоя",
                            id = "eID:0001",
                            permissions = mutableSetOf(Permissions.READ)
                        ),
                        sets = listOf(
                            OneSet(
                                performance = listOf(
                                    Performance(
                                        weight = 100.0,
                                        repetition = 10
                                    )
                                ),
                                status = OneSet.Status.PLAN,
                                modificationExercise = OneSet.ModificationExercise.NONE
                            )
                        ),
                        modificationBlockExercises = ExercisesBlock.ModificationBlockExercises.NONE
                    )
                )
            ),
            debug = BaseDebugRequest(
                mode = BaseDebugRequest.Mode.STUB,
                stubCase = BaseDebugRequest.StubCase.SUCCESS
            )
        )
    }
}