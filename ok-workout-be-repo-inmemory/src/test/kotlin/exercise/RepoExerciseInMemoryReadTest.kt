package exercise

import RepoExerciseInMemory
import ru.otus.otuskotlin.workout.be.repo.test.exercise.RepoExerciseReadTest
import ru.workout.otuskotlin.workout.backend.common.repo.common.exercise.IRepoExercise

class RepoExerciseInMemoryReadTest : RepoExerciseReadTest() {
    override val repo: IRepoExercise = RepoExerciseInMemory(initObjects = initObjects)
}