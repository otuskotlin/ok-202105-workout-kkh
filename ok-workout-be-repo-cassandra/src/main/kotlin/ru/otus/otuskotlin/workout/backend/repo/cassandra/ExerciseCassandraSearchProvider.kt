package ru.otus.otuskotlin.workout.backend.repo.cassandra

import com.datastax.oss.driver.api.core.cql.AsyncResultSet
import com.datastax.oss.driver.api.mapper.MapperContext
import com.datastax.oss.driver.api.mapper.entity.EntityHelper
import com.datastax.oss.driver.api.querybuilder.QueryBuilder
import ru.otus.otuskotlin.workout.backend.repo.common.exercise.DbExerciseFilterRequest
import java.util.concurrent.CompletableFuture
import java.util.concurrent.CompletionStage
import java.util.function.BiConsumer

class ExerciseCassandraSearchProvider(
    private val context: MapperContext,
    private val entityHelper: EntityHelper<ExerciseCassandraDTO>
) {

    fun search(
        req: DbExerciseFilterRequest
    ): CompletionStage<Collection<ExerciseCassandraDTO>> {
        var select = entityHelper.selectStart().allowFiltering()
        if (req.searchStr.isNotBlank()) {
            when (req.mode) {
                DbExerciseFilterRequest.SearchMode.DESCRIPTION -> {
                    select = select
                        .whereColumn(ExerciseCassandraDTO.COLUMN_DESCRIPTION)
                        .like(QueryBuilder.literal("%${req.searchStr}%"))
                    println(select)
                }
                DbExerciseFilterRequest.SearchMode.TITLE -> {
                    select = select
                        .whereColumn(ExerciseCassandraDTO.COLUMN_TITLE)
                        .like(QueryBuilder.literal("%${req.searchStr}%"))
                    println(select)
                }
//                DbExerciseFilterRequest.SearchMode.NONE -> {
//                    select = select
//                        .whereColumn(ExerciseCassandraDTO.COLUMN_DESCRIPTION)
//                        .like(QueryBuilder.literal("%${req.searchStr}%"))
//                        .whereColumn(ExerciseCassandraDTO.COLUMN_TITLE)
//                        .like(QueryBuilder.literal("%${req.searchStr}%"))
//                    println("select: $select")
//                }
            }
        }

        val fetcher = CollectionFetcher()

        context.session
            .executeAsync(select.build())
            .whenComplete(fetcher)

        return fetcher.resultStage
    }

    inner class CollectionFetcher : BiConsumer<AsyncResultSet?, Throwable?> {
        private val buffer = mutableListOf<ExerciseCassandraDTO>()
        private val resultFuture = CompletableFuture<Collection<ExerciseCassandraDTO>>()
        val resultStage: CompletionStage<Collection<ExerciseCassandraDTO>> = resultFuture

        override fun accept(resultSet: AsyncResultSet?, t: Throwable?) {
            when {
                t != null -> resultFuture.completeExceptionally(t)
                resultSet == null -> resultFuture.completeExceptionally(IllegalStateException("Result set is null"))
                else -> {
                    buffer.addAll(resultSet.currentPage().map { entityHelper.get(it, false) })
                    if (resultSet.hasMorePages()) {
                        resultSet.fetchNextPage().whenComplete(this)
                    } else {
                        resultFuture.complete(buffer)
                    }
                }
            }
        }
    }
}