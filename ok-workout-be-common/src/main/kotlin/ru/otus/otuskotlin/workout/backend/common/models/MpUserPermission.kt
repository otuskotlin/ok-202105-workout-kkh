package ru.otus.otuskotlin.workout.backend.common.models

enum class MpUserPermission {
    CREATE_OWN,

    READ_OWN,
    READ_PUBLIC,

    UPDATE_OWN,
    UPDATE_PUBLIC,

    DELETE_OWN,
    DELETE_PUBLIC,

    SEARCH_OWN,
    SEARCH_PUBLIC
}
