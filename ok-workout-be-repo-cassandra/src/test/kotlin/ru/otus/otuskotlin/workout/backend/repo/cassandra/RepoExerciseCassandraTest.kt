package ru.otus.otuskotlin.workout.backend.repo.cassandra

import ru.otus.otuskotlin.workout.backend.common.models.ExerciseModel
import ru.otus.otuskotlin.workout.backend.common.repo.common.exercise.IRepoExercise
import ru.otus.otuskotlin.workout.be.repo.test.exercise.*

class RepoAddCassandraCreateTest: RepoExerciseCreateTest() {
    override val repo: IRepoExercise = TestCompanion.createRepo(initObjects)

}

class RepoAddCassandraReadTest: RepoExerciseReadTest() {
    override val repo: IRepoExercise = TestCompanion.createRepo(initObjects)

}

class RepoAddCassandraUpdateTest: RepoExerciseUpdateTest() {
    override val repo: IRepoExercise = TestCompanion.createRepo(initObjects)

}

class RepoAddCassandraDeleteTest: RepoExerciseDeleteTest() {
    override val repo: IRepoExercise = TestCompanion.createRepo(initObjects)

}

class RepoAddCassandraSearchTest: RepoExerciseSearchTest() {
    override val repo: IRepoExercise = TestCompanion.createRepo(initObjects)

}

object TestCompanion {
    fun createRepo(initObjects: List<ExerciseModel>): RepoExerciseCassandra = RepoExerciseCassandra()
}