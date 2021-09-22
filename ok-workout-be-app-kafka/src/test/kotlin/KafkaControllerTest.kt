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
import ru.otus.otuskotlin.workout.backend.logics.ExerciseCrud
import ru.otus.otuskotlin.workout.backend.logics.WorkoutCrud
import ru.otus.otuskotlin.workout.openapi.models.*
import java.util.*
import kotlin.test.assertEquals

class KafkaControllerTest {
    @Test
    fun kafkaCreateExercise() {
        val consumer = MockConsumer<String, String>(OffsetResetStrategy.EARLIEST)
        val producer = MockProducer(true, StringSerializer(), StringSerializer())
        val config = AppKafkaConfig(
            kafkaConsumer = consumer,
            kafkaProducer = producer
        )
        val app = KafkaApplication(config)

        consumer.schedulePollTask {
            consumer.rebalance(
                listOf(
                    TopicPartition(config.kafkaTopicsIn[0], 0),
                    TopicPartition(config.kafkaTopicsIn[1], 0)
                )
            )

            consumer.addRecord(
                ConsumerRecord(
                    config.kafkaTopicsIn[0],
                    PARTITION,
                    0L,
                    "create-exercise-1",
                    CreateExerciseRequest(
                        requestId = "rId:0101",
                        createExercise = CreatableExercise(
                            title = "Жим штанги лёжа",
                            description = "Базовое упражнение, выполняется на горизонтальной скамье",
                            targetMuscleGroup = listOf("Грудные"),
                            synergisticMuscleGroup = listOf("Трицепс"),
                            executionTechnique = "Подконтрольно опускаем на уровень груди, выжимаем вертикально вверх"
                        ),
                        debug = BaseDebugRequest(
                            mode = BaseDebugRequest.Mode.STUB,
                            stubCase = BaseDebugRequest.StubCase.SUCCESS
                        )
                    ).toJson()
                )
            )

            consumer.addRecord(
                ConsumerRecord(
                    config.kafkaTopicsIn[0],
                    PARTITION,
                    1L,
                    "create-exercise-2",
                    CreateExerciseRequest(
                        requestId = "rId:0102",
                        createExercise = CreatableExercise(
                            title = "Жим штанги лёжа на наклонной скамье",
                            description = "Базовое упражнение, выполняется на наклонной скамье",
                            targetMuscleGroup = listOf("Грудные"),
                            synergisticMuscleGroup = listOf("Трицепс"),
                            executionTechnique = "Подконтрольно опускаем на уровень груди, выжимаем вертикально вверх"
                        ),
                        debug = BaseDebugRequest(
                            mode = BaseDebugRequest.Mode.STUB,
                            stubCase = BaseDebugRequest.StubCase.SUCCESS
                        )
                    ).toJson()
                )
            )

            consumer.addRecord(
                ConsumerRecord(
                    config.kafkaTopicsIn[0],
                    PARTITION,
                    2L,
                    "read-exercise-1",
                    ReadExerciseRequest(
                        requestId = "rId:0103",
                        readExerciseId = "eId:0201",
                        debug = BaseDebugRequest(
                            mode = BaseDebugRequest.Mode.STUB,
                            stubCase = BaseDebugRequest.StubCase.SUCCESS
                        )
                    ).toJson()
                )
            )

            consumer.addRecord(
                ConsumerRecord(
                    config.kafkaTopicsIn[1],
                    PARTITION,
                    0L,
                    "read-workout-1",
                    ReadWorkoutRequest(
                        requestId = "rId:0104",
                        readWorkoutId = "wId:0201",
                        debug = BaseDebugRequest(
                            mode = BaseDebugRequest.Mode.STUB,
                            stubCase = BaseDebugRequest.StubCase.SUCCESS
                        )
                    ).toJson()
                )
            )

            consumer.addRecord(
                ConsumerRecord(
                    config.kafkaTopicsIn[1],
                    PARTITION,
                    1L,
                    "read-workout-2",
                    ReadWorkoutRequest(
                        requestId = "rId:0105",
                        readWorkoutId = "wId:0201",
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


        val tpExercise = TopicPartition(config.kafkaTopicsIn[0], PARTITION)
        startOffsets[tpExercise] = 0L
        consumer.updateBeginningOffsets(startOffsets)

        val tpWorkout = TopicPartition(config.kafkaTopicsIn[1], PARTITION)
        startOffsets[tpWorkout] = 0L
        consumer.updateBeginningOffsets(startOffsets)

        println("startOffsets: $startOffsets")

        app.run()

        val messageOne = producer.history()[0]
        val messageTwo = producer.history()[1]
        val messageThree = producer.history()[2]
        val messageFour = producer.history()[3]
        val messageFive = producer.history()[4]

        println(messageOne)
        println(messageTwo)
        println(messageThree)
        println(messageFour)
        println(messageFive)

        val resultOne = messageOne.value().fromJson<CreateExerciseResponse>()
        val resultTwo = messageTwo.value().fromJson<CreateExerciseResponse>()
        val resultThree = messageThree.value().fromJson<ReadExerciseResponse>()

        assertEquals("rId:0101", resultOne.requestId)
        assertEquals("Жим штанги лёжа", resultOne.createdExercise?.title)

        assertEquals("rId:0102", resultTwo.requestId)
        assertEquals("Жим штанги лёжа на наклонной скамье", resultTwo.createdExercise?.title)

        assertEquals("rId:0103", resultThree.requestId)
        assertEquals("Приседания со штангой", resultThree.readExercise?.title)

        assertEquals(producer.history().size, 5)
    }

    companion object {
        const val PARTITION = 0
    }
}

private val om = ObjectMapper()
private fun BaseMessage.toJson(): String = om.writeValueAsString(this)
private inline fun <reified T : BaseMessage> String.fromJson() = om.readValue(this, T::class.java)