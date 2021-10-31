package ru.otus.otuskotlin.workout.backend.common.models

import java.util.*

@JvmInline
value class AuthorIdModel(val id: String) {
    constructor(id: UUID) : this(id.toString())

    companion object {
        val NONE = AuthorIdModel("")
    }

    fun asString() = id

    fun asUUID(): UUID = UUID.fromString(id)
}
