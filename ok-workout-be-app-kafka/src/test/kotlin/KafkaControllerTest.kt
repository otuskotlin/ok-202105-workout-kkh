import com.fasterxml.jackson.databind.ObjectMapper
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.apache.kafka.clients.consumer.MockConsumer
import org.apache.kafka.clients.consumer.OffsetResetStrategy
import org.apache.kafka.clients.producer.MockProducer
import org.apache.kafka.common.TopicPartition
import org.apache.kafka.common.serialization.StringSerializer
import org.junit.Test
import ru.otus.otuskotlin.workout.app.kafka.AppKafkaConfig
import ru.otus.otuskotlin.workout.app.kafka.KafkaApplication
import ru.otus.otuskotlin.workout.openapi.models.*
import java.util.*
import kotlin.test.assertEquals

class KafkaControllerTest {
    @Test
    fun funKafka() {
        val consumer = MockConsumer<String, String>(OffsetResetStrategy.EARLIEST)
        val producer = MockProducer<String, String>(true, StringSerializer(), StringSerializer())
        val config = AppKafkaConfig(
            kafkaConsumer = consumer,
            kafkaProducer = producer
        )
        val app = KafkaApplication(config)
        consumer.schedulePollTask {
            consumer.rebalance(Collections.singletonList(TopicPartition(config.kafkaTopicIn, 0)))
            consumer.addRecord(
                ConsumerRecord(
                    config.kafkaTopicIn,
                    PARTITION,
                    0L,
                    "test-1",
                    CreateExerciseRequest(
                        requestId = "123",
                        createExercise = CreatableExercise(
                            title = "Some exercise",
                            description = "Some description",
                            targetMuscleGroup = listOf("Some muscle group"),
                            synergisticMuscleGroup = listOf("Some muscle group"),
                            executionTechnique = "Some technique"
                        ),
                        debug = BaseDebugRequest(
                            mode = BaseDebugRequest.Mode.STUB,
                            stubCase = BaseDebugRequest.StubCase.SUCCESS
                        )
                    ).toJson()
                )
            )
            app.stop()
        }

        val startOffsets: MutableMap<TopicPartition, Long> = mutableMapOf()

        val tp = TopicPartition(config.kafkaTopicIn, PARTITION)
        startOffsets[tp] = 0L
        consumer.updateBeginningOffsets(startOffsets)

        app.run()

        val message = producer.history().first()
        println("message: $message")
        val result = message.value().fromJson<CreateExerciseResponse>()
        println(result)
        assertEquals("123", result.requestId)
        assertEquals("Some exercise", result.createdExercise?.title)
    }

    companion object {
        const val PARTITION = 0
    }
}

private val om = ObjectMapper()
private fun BaseMessage.toJson(): String = om.writeValueAsString(this)
private inline fun <reified T : BaseMessage> String.fromJson() = om.readValue<T>(this, T::class.java)