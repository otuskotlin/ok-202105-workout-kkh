import com.fasterxml.jackson.databind.ObjectMapper
import ru.otus.otuskotlin.workout.openapi.models.CreatableExercise
import ru.otus.otuskotlin.workout.openapi.models.CreateExerciseRequest
import kotlin.test.Test
import kotlin.test.assertTrue

class SerialisationTest {
    private val requestId = "id:123"
    private val createRequest = CreateExerciseRequest(
        requestId = requestId,
        createExercise = CreatableExercise(
            title = "Приседания со штангой",
            description = "Базовое упражнение",
            targetMuscleGroup = listOf("Квадрицепсы"),
            synergisticMuscleGroup = listOf("Большие ягодичные", "Приводящие бедра", "Камбаловидные"),
            executionTechnique = "При выполнении приседаний можно варьировать положение ног, но обычно рекомендуют ставить носки врозь, а ноги примерно на ширине плеч"
        )
    )
    private val om = ObjectMapper()

    @Test
    fun serialisationTest() {
        val json = om.writeValueAsString(createRequest)
        println(json)
        assertTrue("json must contain discriminator") {
            json.contains(""""messageType":"${createRequest::class.simpleName}"""")
        }
    }
}