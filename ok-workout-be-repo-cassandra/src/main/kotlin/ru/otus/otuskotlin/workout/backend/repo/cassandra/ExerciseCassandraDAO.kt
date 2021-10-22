package ru.otus.otuskotlin.workout.backend.repo.cassandra

import com.datastax.oss.driver.api.mapper.annotations.*
import ru.otus.otuskotlin.workout.backend.common.repo.common.exercise.DbExerciseFilterRequest
import java.util.concurrent.CompletionStage

@Dao
interface ExerciseCassandraDAO {
    @Insert
    fun create(dto: ExerciseCassandraDTO): CompletionStage<Void>

    @Select
    fun read(id: String): CompletionStage<ExerciseCassandraDTO?>

    @Update(ifExists = true)
    fun update(dto: ExerciseCassandraDTO): CompletionStage<Void>

    @Delete
    fun delete(dto: ExerciseCassandraDTO): CompletionStage<Void>

    @QueryProvider(
        providerClass = ExerciseCassandraSearchProvider::class,
        entityHelpers = [ExerciseCassandraDTO::class]
    )
    fun search(req: DbExerciseFilterRequest): CompletionStage<Collection<ExerciseCassandraDTO>>
}