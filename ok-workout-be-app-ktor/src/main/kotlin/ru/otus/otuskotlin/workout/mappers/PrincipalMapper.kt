package ru.otus.otuskotlin.workout.mappers

import io.ktor.auth.jwt.*
import ru.otus.otuskotlin.workout.backend.common.models.MpPrincipalModel
import ru.otus.otuskotlin.workout.backend.common.models.MpUserGroups

fun JWTPrincipal?.toModel() = this?.run {
    MpPrincipalModel(
        fname = payload.getClaim("fname").asString() ?: "",
        mname = payload.getClaim("mname").asString() ?: "",
        lname = payload.getClaim("lname").asString() ?: "",
        groups = payload.getClaim("groups")
            ?.asList(String::class.java)
            ?.mapNotNull {
                try {
                    MpUserGroups.valueOf(it)
                } catch (e: Throwable) {
                    null
                }
            }?.toSet() ?: emptySet()
    )
} ?: MpPrincipalModel.NONE