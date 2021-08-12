import ru.otus.otuskotlin.workout.openapi.models.*
import ru.workout.otuskotlin.workout.backend.common.context.BeContext
import ru.workout.otuskotlin.workout.backend.common.models.*
import ru.workout.otuskotlin.workout.backend.common.models.ExercisePermissions
import ru.workout.otuskotlin.workout.backend.mapping.openapi.*
import java.time.LocalDate
import kotlin.test.Test
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

    private val performance = PerformanceModel(
        weight = 80.0,
        measure = PerformanceModel.Measure.KG,
        repetition = 10
    )

    private val oneSet = OneSetModel(
        performance = mutableListOf(performance),
        status = OneSetModel.Status.DONE,
        modificationExercise = OneSetModel.ModificationExercise.NONE
    )

    private val exercisesBlock = ExercisesBlockModel(
        exercise = exerciseModel,
        sets = mutableListOf(oneSet),
        modificationBlockExercises = ModificationBlockExercises.NONE
    )

    private val workoutModel = WorkoutModel(
        date = "2021-08-02",
        duration = 100.0,
        recoveryTime = 90.0,
        modificationWorkout = WorkoutModel.ModificationWorkout.CLASSIC,
        exercisesBlock = mutableListOf(exercisesBlock),
        idWorkout = WorkoutIdModel("wID:0001"),
        permissions = mutableSetOf(ExercisePermissions.CREATE, ExercisePermissions.READ)
    )

    private val beContext = BeContext(
        requestId = "rID:0001",
        responseExercise = exerciseModel,
        responseWorkout = workoutModel

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
    fun createExerciseResponseTest() {
        val response = beContext.toCreateExerciseResponse()
        println(response)
        assertEquals("rID:0001", response.requestId)
        assertEquals(CreateExerciseResponse.Result.SUCCESS, response.result)
        assertTrue(response.errors.isNullOrEmpty())
        assertTrue(response.createdExercise != null)
        assertTrue(response.createdExercise?.title?.isNotBlank() ?: false)
        assertTrue(response.createdExercise?.description?.isNotBlank() ?: false)
        assertTrue(response.createdExercise?.targetMuscleGroup?.isNotEmpty() ?: false)
        assertTrue(response.createdExercise?.synergisticMuscleGroup?.isNotEmpty() ?: false)
        assertTrue(response.createdExercise?.executionTechnique?.isNotBlank() ?: false)
        assertEquals("eID:0001", response.createdExercise?.id)
        assertTrue(response.createdExercise?.permissions?.contains(Permissions.READ) ?: false)
    }

    @Test
    fun updateWorkoutResponseTest() {
        val response = beContext.toUpdateWorkoutResponse()
        println(response)
        println(beContext)
        assertEquals("rID:0001", response.requestId)
        assertEquals(UpdateWorkoutResponse.Result.SUCCESS, response.result)
        assertTrue(response.errors.isNullOrEmpty())
        assertEquals("2021-08-02", response.updateWorkout?.date)
        assertEquals(100.0, response.updateWorkout?.duration)
        assertEquals(90.0, response.updateWorkout?.recoveryTime)
        assertEquals(ResponseWorkout.ModificationWorkout.CLASSIC, response.updateWorkout?.modificationWorkout)
        assertTrue(response.updateWorkout?.exercisesBlock?.isNotEmpty() ?: false)
        assertTrue(response.updateWorkout?.exercisesBlock?.first()?.exercise?.title?.isNotBlank() ?: false)
        assertTrue(response.updateWorkout?.exercisesBlock?.first()?.exercise?.description?.isNotBlank() ?: false)
        assertTrue(response.updateWorkout?.exercisesBlock?.first()?.exercise?.targetMuscleGroup?.isNotEmpty() ?: false)
        assertTrue(
            response.updateWorkout?.exercisesBlock?.first()?.exercise?.synergisticMuscleGroup?.isNotEmpty() ?: false
        )
        assertTrue(response.updateWorkout?.exercisesBlock?.first()?.exercise?.executionTechnique?.isNotBlank() ?: false)
        assertEquals("eID:0001", response.updateWorkout?.exercisesBlock?.first()?.exercise?.id)
        assertTrue(
            response.updateWorkout?.exercisesBlock?.first()?.exercise?.permissions?.contains(Permissions.READ)
                ?: false
        )
        assertEquals("wID:0001", response.updateWorkout?.id)
        assertTrue(
            response.updateWorkout?.permissions?.containsAll(listOf(Permissions.CREATE, Permissions.READ)) ?: false
        )

    }
}
