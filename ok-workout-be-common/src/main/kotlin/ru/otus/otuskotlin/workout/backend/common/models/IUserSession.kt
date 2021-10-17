package ru.otus.otuskotlin.workout.backend.common.models

import ru.otus.otuskotlin.workout.backend.common.context.BeContext

interface IUserSession<T> {
    val fwSession: T

    suspend fun notifyAdChanged(context: BeContext)

    companion object {
        object EmptySession : IUserSession<Unit> {
            override val fwSession: Unit = Unit

            override suspend fun notifyAdChanged(context: BeContext) {
                TODO("Not yet implemented")
            }
        }
    }
}