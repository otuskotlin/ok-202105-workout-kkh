import com.fasterxml.jackson.databind.ObjectMapper
import ru.otus.otuskotlin.workout.openapi.models.*
import kotlin.test.Test
import kotlin.test.assertContains
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class SerialisationTest {
    private val requestId = "id:123"
    private val exercise1 = CreatableExercise(
        title = "Приседания со штангой",
        description = "Базовое упражнение",
        targetMuscleGroup = listOf("Квадрицепсы"),
        synergisticMuscleGroup = listOf("Большие ягодичные", "Приводящие бедра", "Камбаловидные"),
        executionTechnique = "При выполнении приседаний можно варьировать положение ног, но обычно рекомендуют ставить носки врозь, а ноги примерно на ширине плеч"
    )
    private val createExerciseRequest = CreateExerciseRequest(
        requestId = requestId,
        createExercise = exercise1
    )
    private val createWorkoutRequest = CreateWorkoutRequest(
        requestId = requestId,
        createWorkout = CreatableWorkout(
            date = "2021-08-11",
            duration = 0.0,
            recoveryTime = 60.0,
            modificationWorkout = CreatableWorkout.ModificationWorkout.CLASSIC,
            exercisesBlock = mutableListOf(
                ExercisesBlock(
                    exercise = exercise1,
                    sets = mutableListOf(
                        OneSet(
                            performance = mutableListOf(
                                Performance(
                                    weight = 80.0,
                                    measure = Performance.Measure.KG,
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
        )
    )

    private val om = ObjectMapper()

    @Test
    fun serialisationExerciseTest() {
        val json = om.writeValueAsString(createExerciseRequest)
        println(json)
        assertContains(json, Regex("messageType\":\\s*\"${CreateExerciseRequest::class.simpleName}"))
    }

    @Test
    fun deserializationExerciseTest() {
        val json = om.writeValueAsString(createExerciseRequest)
        val deserialized = om.readValue(json, BaseMessage::class.java) as CreateExerciseRequest
        println(deserialized)
        assertEquals(requestId, deserialized.requestId)
    }

    @Test
    fun serializationWorkoutTest() {
        val json = om.writeValueAsString(createWorkoutRequest)
        println(json)
        assertTrue("json must contain discriminator") {
            json.contains(""""messageType":"${createWorkoutRequest::class.simpleName}"""")
        }
    }

    @Test
    fun deserializationWorkoutTest() {
        val json = om.writeValueAsString(createWorkoutRequest)
        val deserialized = om.readValue(json, BaseMessage::class.java) as CreateWorkoutRequest
        println(deserialized)
        assertEquals(requestId, deserialized.requestId)
        assertTrue("workout must have one exercise") {
            deserialized.createWorkout?.exercisesBlock?.size == 1
        }
    }

}