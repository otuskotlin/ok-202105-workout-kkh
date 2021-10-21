package ru.otus.otuskotlin.workout.backend.repo.cassandra

import com.datastax.oss.driver.api.mapper.annotations.DaoFactory
import com.datastax.oss.driver.api.mapper.annotations.DaoKeyspace
import com.datastax.oss.driver.api.mapper.annotations.DaoTable
import com.datastax.oss.driver.api.mapper.annotations.Mapper

@Mapper
interface ExerciseCassandraMapper {
    @DaoFactory
    fun exerciseCassandraDao(
        @DaoKeyspace keyspace: String,
        @DaoTable tableName: String
    ): ExerciseCassandraDAO
}