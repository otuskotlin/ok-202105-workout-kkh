import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import models.ExerciseRow
import org.ehcache.Cache
import org.ehcache.CacheManager
import org.ehcache.config.builders.CacheConfigurationBuilder
import org.ehcache.config.builders.CacheManagerBuilder
import org.ehcache.config.builders.ExpiryPolicyBuilder
import org.ehcache.config.builders.ResourcePoolsBuilder
import ru.otus.otuskotlin.workout.backend.common.models.CommonErrorModel
import ru.otus.otuskotlin.workout.backend.common.models.ExerciseIdModel
import ru.otus.otuskotlin.workout.backend.common.models.ExerciseModel
import ru.otus.otuskotlin.workout.backend.common.repo.common.exercise.*
import java.time.Duration
import java.util.*

class RepoExerciseInMemory(
    private val initObjects: List<ExerciseModel>,
    private val ttl: Duration = Duration.ofMinutes(1)
) : IRepoExercise {

    private val cache: Cache<String, ExerciseRow> = let {
        val cacheManager: CacheManager = CacheManagerBuilder
            .newCacheManagerBuilder()
            .build(true)

        cacheManager.createCache(
            "workout-exercise-cache",
            CacheConfigurationBuilder
                .newCacheConfigurationBuilder(
                    String::class.java,
                    ExerciseRow::class.java,
                    ResourcePoolsBuilder.heap(100)
                )
                .withExpiry(ExpiryPolicyBuilder.timeToLiveExpiration(ttl))
                .build()
        )
    }

    init {
        runBlocking {
            initObjects.forEach {
                save(it)
            }
        }
    }

    private suspend fun save(item: ExerciseModel): DbExerciseResponse {
        val row = ExerciseRow(item)
        if (row.idExercise == null) {
            return DbExerciseResponse(
                result = null,
                errors = listOf(
                    CommonErrorModel(
                        field = "id",
                        message = "Id most not be null or blank"
                    )
                ),
                isSuccess = false
            )
        }
        cache.put(row.idExercise, row)
        return DbExerciseResponse(
            result = row.toInternal(),
            isSuccess = true
        )
    }

    override suspend fun create(req: DbExerciseModelRequest): DbExerciseResponse = save(
        req.exercise.copy(
            idExercise = ExerciseIdModel(
                UUID.randomUUID().toString()
            )
        )
    )

    override suspend fun read(req: DbExerciseIdRequest): DbExerciseResponse = cache.get(req.id.asString())?.let {
        DbExerciseResponse(
            isSuccess = true,
            result = it.toInternal()
        )
    } ?: DbExerciseResponse(
        isSuccess = false,
        errors = listOf(
            CommonErrorModel(
                field = "id",
                message = "Not Found"
            )
        ),
        result = null
    )

    override suspend fun update(req: DbExerciseModelRequest): DbExerciseResponse {
        val key =
            req.exercise.idExercise.takeIf { it != ExerciseIdModel.NONE }?.asString()
                ?: return DbExerciseResponse(
                    isSuccess = false,
                    errors = listOf(
                        CommonErrorModel(
                            field = "id",
                            message = "Id must not be null or blank"
                        )
                    ),
                    result = null
                )

        return if (cache.containsKey(key)) {
            save(req.exercise)
            DbExerciseResponse(
                result = req.exercise,
                isSuccess = true
            )
        } else {
            DbExerciseResponse(
                isSuccess = false,
                errors = listOf(
                    CommonErrorModel(
                        field = "id",
                        message = "Not Found"
                    )
                ),
                result = null
            )
        }
    }

    override suspend fun delete(req: DbExerciseIdRequest): DbExerciseResponse {
        val key = req.id.takeIf { it != ExerciseIdModel.NONE }?.asString()
            ?: return DbExerciseResponse(
                isSuccess = false,
                errors = listOf(
                    CommonErrorModel(
                        field = "id",
                        message = "Id must not be null or blank"
                    )
                ),
                result = null
            )

        val row = cache.get(key)
            ?: return DbExerciseResponse(
                isSuccess = false,
                errors = listOf(
                    CommonErrorModel(
                        field = "id",
                        message = "Not Found",
                    )
                ),
                result = null
            )

        cache.remove(key)
        return DbExerciseResponse(
            isSuccess = true,
            result = row.toInternal()
        )
    }

    override suspend fun search(req: DbExerciseFilterRequest): DbExercisesResponse {
        val results = cache.asFlow()
            .filter {
                when (req.mode) {
                    DbExerciseFilterRequest.SearchMode.NONE ->
                        it.value.title?.lowercase()?.contains(req.searchStr.lowercase()) == true
                                || it.value.description?.lowercase()?.contains(req.searchStr.lowercase()) == true
                    DbExerciseFilterRequest.SearchMode.TITLE ->
                        it.value.title?.lowercase()?.contains(req.searchStr.lowercase()) == true
                    DbExerciseFilterRequest.SearchMode.DESCRIPTION ->
                        it.value.description?.lowercase()?.contains(req.searchStr.lowercase()) == true
                }
            }
            .map { it.value.toInternal() }
            .toList()
        return DbExercisesResponse(
            isSuccess = true,
            result = results
        )
    }
}