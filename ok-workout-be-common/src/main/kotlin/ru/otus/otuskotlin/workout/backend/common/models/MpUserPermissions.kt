package ru.otus.otuskotlin.workout.backend.common.models

enum class MpUserPermissions {
    CREATE_AUTHOR,

    READ_AUTHOR,
    READ_PUBLIC,

    UPDATE_AUTHOR,
    UPDATE_PUBLIC,

    DELETE_AUTHOR,
    DELETE_PUBLIC,

    SEARCH_AUTHOR,
    SEARCH_PUBLIC
}
