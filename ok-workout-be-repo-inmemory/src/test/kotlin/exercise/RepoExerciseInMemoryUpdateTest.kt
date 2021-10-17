package exercise

import ru.otus.otuskotlin.workout.be.repo.inmemory.models.RepoExerciseInMemory
import ru.otus.otuskotlin.workout.be.repo.test.exercise.RepoExerciseUpdateTest
import ru.otus.otuskotlin.workout.backend.common.repo.common.exercise.IRepoExercise

class RepoExerciseInMemoryUpdateTest : RepoExerciseUpdateTest() {
    override val repo: IRepoExercise = RepoExerciseInMemory(
        initObjects = initObjects
    )
}