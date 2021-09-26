package ru.otus.otuskotlin.workout.app.kafka

import com.fasterxml.jackson.databind.ObjectMapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.NonCancellable
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.apache.kafka.clients.consumer.ConsumerRecords
import org.apache.kafka.clients.producer.ProducerRecord
import org.apache.kafka.common.errors.WakeupException
import ru.otus.otuskotlin.workout.openapi.models.BaseMessage
import ru.workout.otuskotlin.workout.backend.common.context.BeContext
import java.time.Duration
import java.time.Instant
import java.util.*
import java.util.concurrent.atomic.AtomicBoolean

class KafkaApplication(private val config: AppKafkaConfig) {
    private val consumer = config.kafkaConsumer
    private val producer = config.kafkaProducer
    private val services = config.services
    private val om = ObjectMapper()
    private val process = AtomicBoolean(true)

    fun run() = runBlocking {
        try {
            consumer.subscribe(config.kafkaTopicsIn)
            while (process.get()) {
                try {
                    val records: ConsumerRecords<String, String> = consumer.poll(Duration.ofSeconds(1))
                    records.forEach { record: ConsumerRecord<String, String> ->
                        val context = BeContext(
                            startTime = Instant.now()
                        )

                        val service = when (record.topic()) {
                            "exercise-in" -> services[0]
                            "workout-in" -> services[1]
                            else -> {
                                throw Exception("Service does not exist")
                            }
                        }

                        try {
                            val request =
                                withContext(Dispatchers.IO) {
                                    om.readValue(record.value(), BaseMessage::class.java)
                                }
                            sendResponse(service.handleRequest(context, request))
                        } catch (t: Throwable) {
                            sendResponse(service.handleError(context, t))
                        }
                    }
                } catch (t: Throwable) {
                    println(t.message)
                }
            }
        } catch (ex: WakeupException) {
            println(ex.message)
        } catch (ex: RuntimeException) {
            withContext(NonCancellable) {
                throw ex
            }
        } finally {
            withContext(NonCancellable) {
                consumer.close()
            }
        }
    }

    private suspend fun sendResponse(response: BaseMessage) {

        val json = withContext(Dispatchers.IO) {
            om.writeValueAsString(response)
        }

        val topic = if (json.contains("ExerciseResponse")) {
            config.kafkaTopicsOut[0]
        } else {
            config.kafkaTopicsOut[1]
        }

        val resRecord = ProducerRecord(
            topic,
            UUID.randomUUID().toString(),
            json
        )
        producer.send(resRecord)
    }

    fun stop() {
        process.set(false)
    }
}