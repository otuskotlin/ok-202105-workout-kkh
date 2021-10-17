package ru.otus.otuskotlin.workout.backend.common.models

import java.util.*

@JvmInline
value class ExerciseIdModel(private val id: String) {
    constructor(id: UUID) : this(id.toString())
    companion object {
        val NONE = ExerciseIdModel("")
    }

    fun asString() = toString()

    override fun toString(): String = id
}
