package exercise

import RepoExerciseInMemory
import ru.otus.otuskotlin.workout.be.repo.test.exercise.RepoExerciseCreateTest
import ru.workout.otuskotlin.workout.backend.common.repo.common.exercise.IRepoExercise

class RepoExerciseInMemoryCreateTest: RepoExerciseCreateTest() {
    override val repo: IRepoExercise = RepoExerciseInMemory(initObjects = initObjects)
}