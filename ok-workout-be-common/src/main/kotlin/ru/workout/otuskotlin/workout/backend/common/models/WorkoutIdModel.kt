package ru.workout.otuskotlin.workout.backend.common.models

@JvmInline
value class WorkoutIdModel(private val id: String) {
    companion object {
        val NONE = WorkoutIdModel("")
    }

    override fun toString(): String = id
}
