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
import ru.otus.otuskotlin.workout.backend.common.context.BeContext
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
                            config.kafkaTopicsIn[0] -> services[0]
                            config.kafkaTopicsIn[1] -> services[1]
                            else -> {
                                throw Exception("Service does not exist")
                            }
                        }

                        try {
                            val request =
                                withContext(Dispatchers.IO) {
                                    om.readValue(record.value(), BaseMessage::class.java)
                                }
                            sendResponse(record.topic(), service.handleRequest(context, request))
                        } catch (t: Throwable) {
                            sendResponse(record.topic(), service.handleError(context, t))
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

    private suspend fun sendResponse(topicIn: String, response: BaseMessage) {

        val topicOut = when (topicIn) {
            config.kafkaTopicsIn[0] -> config.kafkaTopicsOut[0]
            config.kafkaTopicsIn[1] -> config.kafkaTopicsOut[1]
            else -> throw Exception("Unknown messageType")
        }

        val json = withContext(Dispatchers.IO) {
            om.writeValueAsString(response)
        }

        val resRecord = ProducerRecord(
            topicOut,
            UUID.randomUUID().toString(),
            json
        )
        producer.send(resRecord)
    }

    fun stop() {
        process.set(false)
    }
}