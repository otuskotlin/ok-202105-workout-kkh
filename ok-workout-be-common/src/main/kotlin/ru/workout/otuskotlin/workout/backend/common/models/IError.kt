package ru.workout.otuskotlin.workout.backend.common.models

interface IError {
    val field: String
    val level: Level
    val message: String
    val stackTrace: StackTrace

    @JvmInline
    value class StackTrace(private val trace: String) {
        constructor(stackTrace: Array<StackTraceElement>) : this(stackTrace.joinToString("\n"))

        private fun asString() = trace
        override fun toString() = asString()

        companion object {
            val NONE = StackTrace("")
        }
    }

    enum class Level {
        ERROR,
        WARNING
    }
}