import kotlinx.coroutines.runBlocking
import models.ExerciseRow
import org.ehcache.Cache
import org.ehcache.CacheManager
import org.ehcache.config.builders.CacheConfigurationBuilder
import org.ehcache.config.builders.CacheManagerBuilder
import org.ehcache.config.builders.ExpiryPolicyBuilder
import org.ehcache.config.builders.ResourcePoolsBuilder
import ru.workout.otuskotlin.workout.backend.common.models.CommonErrorModel
import ru.workout.otuskotlin.workout.backend.common.models.ExerciseIdModel
import ru.workout.otuskotlin.workout.backend.common.models.ExerciseModel
import ru.workout.otuskotlin.workout.backend.common.repo.common.exercise.*
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

    override suspend fun read(req: DbExerciseIdRequest): DbExerciseResponse {
        TODO("Not yet implemented")
    }

    override suspend fun update(req: DbExerciseModelRequest): DbExerciseResponse {
        TODO("Not yet implemented")
    }

    override suspend fun delete(req: DbExerciseIdRequest): DbExerciseResponse {
        TODO("Not yet implemented")
    }

    override suspend fun search(req: DbExerciseFilterRequest): DbExercisesResponse {
        TODO("Not yet implemented")
    }

}