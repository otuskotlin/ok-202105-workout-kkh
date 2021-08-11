import ru.otus.otuskotlin.workout.openapi.models.SearchWorkoutRequest
import ru.workout.otuskotlin.workout.backend.common.context.BeContext
import ru.workout.otuskotlin.workout.backend.mapping.openapi.setQuery
import java.time.LocalDate
import kotlin.test.Test
import kotlin.test.assertTrue

class MappingTest {

    private val date: String = "2021-08-01"

    private val beContext = BeContext()
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
}