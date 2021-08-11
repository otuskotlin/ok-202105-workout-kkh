package ru.workout.otuskotlin.workout.backend.common.models

@JvmInline
value class ExerciseIdModel(private val id: String) {
    companion object {
        val NONE = ExerciseIdModel("")
    }

    override fun toString(): String = id
}