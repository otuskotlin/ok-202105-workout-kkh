package exercise

import RepoExerciseInMemory
import ru.otus.otuskotlin.workout.be.repo.test.exercise.RepoExerciseSearchTest
import ru.workout.otuskotlin.workout.backend.common.repo.common.exercise.IRepoExercise

class RepoExerciseInMemorySearchTest : RepoExerciseSearchTest() {
    override val repo: IRepoExercise = RepoExerciseInMemory(initObjects = initObjects)
}