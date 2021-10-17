package exercise

import RepoExerciseInMemory
import ru.otus.otuskotlin.workout.be.repo.test.exercise.RepoExerciseDeleteTest
import ru.otus.otuskotlin.workout.backend.common.repo.common.exercise.IRepoExercise

class RepoExerciseInMemoryDeleteTest : RepoExerciseDeleteTest() {
    override val repo: IRepoExercise = RepoExerciseInMemory(initObjects = initObjects)
}