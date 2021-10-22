package ru.otus.otuskotlin.workout.backend.repo.cassandra

import com.datastax.oss.driver.api.querybuilder.SchemaBuilder
import com.datastax.oss.driver.api.core.CqlSession
import com.datastax.oss.driver.internal.core.type.codec.extras.enums.EnumNameCodec
import com.datastax.oss.driver.internal.core.util.concurrent.CompletableFutures
import org.testcontainers.containers.CassandraContainer
import ru.otus.otuskotlin.workout.backend.common.models.ExerciseModel
import ru.otus.otuskotlin.workout.backend.common.models.ExercisePermissions
import ru.otus.otuskotlin.workout.backend.repo.common.exercise.IRepoExercise
import ru.otus.otuskotlin.workout.be.repo.test.exercise.*
import java.net.InetSocketAddress

class RepoAddCassandraCreateTest : RepoExerciseCreateTest() {
    override val repo: IRepoExercise = TestCompanion.createRepo(initObjects)
}

class RepoAddCassandraReadTest : RepoExerciseReadTest() {
    override val repo: IRepoExercise = TestCompanion.createRepo(initObjects)
}

class RepoAddCassandraUpdateTest : RepoExerciseUpdateTest() {
    override val repo: IRepoExercise = TestCompanion.createRepo(initObjects)
}

class RepoAddCassandraDeleteTest : RepoExerciseDeleteTest() {
    override val repo: IRepoExercise = TestCompanion.createRepo(initObjects)
}

class RepoAddCassandraSearchTest : RepoExerciseSearchTest() {
    override val repo: IRepoExercise = TestCompanion.createRepo(initObjects)
}

class TestCassandraContainer : CassandraContainer<TestCassandraContainer>("cassandra:3.11.2")

object TestCompanion {
    val container by lazy {
        TestCassandraContainer().apply { start() }
    }

    val session by lazy {
        CqlSession.builder()
            .addContactPoint(InetSocketAddress(container.host, container.getMappedPort(CassandraContainer.CQL_PORT)))
            .addTypeCodecs(EnumNameCodec(ExercisePermissions::class.java))
            .withLocalDatacenter("datacenter1")
            .withAuthCredentials(container.username, container.password)
            .build()
    }

    val mapper: ExerciseCassandraMapper by lazy {
        ExerciseCassandraMapperBuilder(session).build()
    }

    fun createRepo(initObjects: List<ExerciseModel>): RepoExerciseCassandra {
        val keyspace = "data"
        val tableName = ExerciseCassandraDTO.TABLE_NAME
        session.execute(
            SchemaBuilder
                .createKeyspace(keyspace)
                .ifNotExists()
                .withSimpleStrategy(1)
                .build()
        )
        session.execute(ExerciseCassandraDTO.table(keyspace, tableName))
        session.execute(ExerciseCassandraDTO.titleIndex(keyspace, tableName))
        session.execute(ExerciseCassandraDTO.descriptionIndex(keyspace, tableName))
        val dao = mapper.exerciseCassandraDao(keyspace, tableName)
        CompletableFutures
            .allDone(initObjects.map { dao.create(ExerciseCassandraDTO(it)) })
            .toCompletableFuture()
            .get()
        return RepoExerciseCassandra(dao)
    }
}