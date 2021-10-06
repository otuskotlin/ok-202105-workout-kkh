import kotlinx.coroutines.runBlocking
import org.junit.Test
import ru.workout.otuskotlin.workout.backend.common.repo.IRepoExercise

abstract class RepoExerciseCreateTest {
    abstract val repo: IRepoExercise

    @Test
    fun createSuccess() {
        val result = runBlocking {

        }
    }


}