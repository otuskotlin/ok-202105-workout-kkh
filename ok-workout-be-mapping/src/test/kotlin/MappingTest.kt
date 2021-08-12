import ru.otus.otuskotlin.workout.openapi.models.InitExerciseResponse
import ru.otus.otuskotlin.workout.openapi.models.InitWorkoutResponse
import ru.otus.otuskotlin.workout.openapi.models.ResponseExercise
import ru.otus.otuskotlin.workout.openapi.models.SearchWorkoutRequest
import ru.workout.otuskotlin.workout.backend.common.context.BeContext
import ru.workout.otuskotlin.workout.backend.common.models.ExerciseIdModel
import ru.workout.otuskotlin.workout.backend.common.models.ExerciseModel
import ru.workout.otuskotlin.workout.backend.common.models.ExercisePermissions
import ru.workout.otuskotlin.workout.backend.mapping.openapi.setQuery
import ru.workout.otuskotlin.workout.backend.mapping.openapi.toCreateExerciseResponse
import ru.workout.otuskotlin.workout.backend.mapping.openapi.toInitExerciseResponse
import ru.workout.otuskotlin.workout.backend.mapping.openapi.toInitWorkoutResponse
import java.time.LocalDate
import kotlin.test.Test
import kotlin.test.assertContains
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class MappingTest {

    private val date: String = "2021-08-01"
    private val exerciseModel = ExerciseModel(
        title = "Приседания со штангой",
        description = "Базовое упражнение",
        targetMuscleGroup = mutableListOf("Квадрицепцсы"),
        synergisticMuscleGroup = mutableListOf("Большие ягодичные", "Приводящие бедра", "Камбаловидные"),
        executionTechnique = "Выполняющий упражнение приседает и затем встаёт, возвращаясь в положение стоя",
        idExercise = ExerciseIdModel("eID:0001"),
        permissions = mutableSetOf(ExercisePermissions.READ)
    )

    private val beContext = BeContext(
        requestId = "rID:0001",
        responseExercise = exerciseModel

    )
    private val searchWorkoutRequest = SearchWorkoutRequest(
        date = date,
        searchMuscleGroup = "Квадрицепсы",
        searchExercise = "Приседания"
    )

    @Test
    fun updateSearchWorkoutRequest() {
        beContext.setQuery(searchWorkoutRequest)
        println(beContext)
        assertTrue("date must be $date") {
            beContext.requestSearchWorkout.date == LocalDate.parse("2021-08-01")
        }
    }

    @Test
    fun initExerciseResponseTest() {
        val response = beContext.toInitExerciseResponse()
        println(response)
        assertEquals("rID:0001", response.requestId)
        assertEquals(InitExerciseResponse.Result.SUCCESS, response.result)
        assertTrue(response.errors.isNullOrEmpty())
    }

    @Test
    fun createExerciseResponseTest() {
        val response = beContext.toCreateExerciseResponse()
        println(response)
        assertEquals("eID:0001", response.createdExercise?.id)
    }

    @Test
    fun initWorkoutResponseTest() {
        val response = beContext.toInitWorkoutResponse()
        println(response)
        assertEquals("rID:0001", response.requestId)
        assertEquals(InitWorkoutResponse.Result.SUCCESS, response.result)
        assertTrue(response.errors.isNullOrEmpty())
    }
}