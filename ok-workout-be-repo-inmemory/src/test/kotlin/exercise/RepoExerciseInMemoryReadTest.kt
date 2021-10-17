package exercise

import ru.otus.otuskotlin.workout.be.repo.inmemory.models.RepoExerciseInMemory
import ru.otus.otuskotlin.workout.be.repo.test.exercise.RepoExerciseReadTest
import ru.otus.otuskotlin.workout.backend.common.repo.common.exercise.IRepoExercise

class RepoExerciseInMemoryReadTest : RepoExerciseReadTest() {
    override val repo: IRepoExercise = RepoExerciseInMemory(initObjects = initObjects)
}