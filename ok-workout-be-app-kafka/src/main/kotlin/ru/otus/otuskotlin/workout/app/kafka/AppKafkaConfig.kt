package ru.otus.otuskotlin.workout.app.kafka

import ExerciseService
import IHandlerRequests
import WorkoutService
import org.apache.kafka.clients.consumer.Consumer
import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.clients.consumer.KafkaConsumer
import org.apache.kafka.clients.producer.KafkaProducer
import org.apache.kafka.clients.producer.Producer
import org.apache.kafka.clients.producer.ProducerConfig
import org.apache.kafka.common.serialization.StringDeserializer
import org.apache.kafka.common.serialization.StringSerializer
import ru.otus.otuskotlin.workout.backend.logics.ExerciseCrud
import ru.otus.otuskotlin.workout.backend.logics.WorkoutCrud
import java.util.*

data class AppKafkaConfig(
    val kafkaHosts: List<String> = KAFKA_HOSTS,
//    val kafkaTopicIn: String = KAFKA_TOPIC_IN,
    val kafkaTopicsIn: List<String> = KAFKA_TOPICS_IN,
//    val kafkaTopicOut: String = KAFKA_TOPIC_OUT,
    val kafkaTopicsOut: List<String> = KAFKA_TOPICS_OUT,
    val kafkaGroupId: String = KAFKA_GROUP_ID,
//    val service: ExerciseService = ExerciseService(crud = ExerciseCrud()),
    val services: List<IHandlerRequests> = listOf(ExerciseService(ExerciseCrud()), WorkoutService(WorkoutCrud())),
    val kafkaConsumer: Consumer<String, String> = kafkaConsumer(kafkaHosts, kafkaGroupId),
    val kafkaProducer: Producer<String, String> = kafkaProducer(kafkaHosts)
) {
    companion object {
        const val KAFKA_HOST_VAR = "KAFKA_HOSTS"

        //        const val KAFKA_TOPIC_IN_VAR = "KAFKA_TOPIC_IN"
        const val KAFKA_TOPICS_IN_VAR = "KAFKA_TOPICS_IN"

        //        const val KAFKA_TOPIC_OUT_VAR = "KAFKA_TOPIC_OUT"
        const val KAFKA_TOPICS_OUT_VAR = "KAFKA_TOPICS_OUT"
        const val KAFKA_GROUP_ID_VAR = "KAFKA_GROUP_ID"

        val KAFKA_HOSTS by lazy { (System.getenv(KAFKA_HOST_VAR) ?: "localhost:9094").split("\\s*[,;]\\s*".toRegex()) }

        //        val KAFKA_TOPIC_IN by lazy { System.getenv(KAFKA_TOPIC_IN_VAR) ?: "workout-exercises-in" }
        val KAFKA_TOPICS_IN by lazy {
            (System.getenv(KAFKA_TOPICS_IN_VAR) ?: "exercise-in;workout-in").split("\\s*[,;]\\s*".toRegex())
        }

        //        val KAFKA_TOPIC_OUT by lazy { System.getenv(KAFKA_TOPIC_OUT_VAR) ?: "workout-exercises-out" }
        val KAFKA_TOPICS_OUT by lazy {
            (System.getenv(KAFKA_TOPICS_OUT_VAR) ?: "exercise-out;workout-out").split("\\s*[,;]\\s*".toRegex())
        }
        val KAFKA_GROUP_ID by lazy { System.getenv(KAFKA_GROUP_ID_VAR) ?: "workout" }

        fun kafkaConsumer(hosts: List<String>, groupId: String): KafkaConsumer<String, String> {
            val props = Properties().apply {
                put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, hosts)
                put(ConsumerConfig.GROUP_ID_CONFIG, groupId)
                put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer::class.java)
                put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer::class.java)
            }
            return KafkaConsumer<String, String>(props)
        }

        fun kafkaProducer(hosts: List<String>): KafkaProducer<String, String> {
            val props = Properties().apply {
                put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, hosts)
                put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer::class.java)
                put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer::class.java)
            }
            return KafkaProducer<String, String>(props)
        }
    }
}