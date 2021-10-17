package exercise

import RepoExerciseInMemory
import ru.otus.otuskotlin.workout.be.repo.test.exercise.RepoExerciseSearchTest
import ru.otus.otuskotlin.workout.backend.common.repo.common.exercise.IRepoExercise

class RepoExerciseInMemorySearchTest : RepoExerciseSearchTest() {
    override val repo: IRepoExercise = RepoExerciseInMemory(initObjects = initObjects)
}