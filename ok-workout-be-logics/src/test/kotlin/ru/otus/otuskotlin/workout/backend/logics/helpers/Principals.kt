package ru.otus.otuskotlin.workout.backend.logics.helpers

import ru.otus.otuskotlin.workout.backend.common.models.AuthorIdModel
import ru.otus.otuskotlin.workout.backend.common.models.MpPrincipalModel
import ru.otus.otuskotlin.workout.backend.common.models.MpUserGroups

fun principalUser(id: AuthorIdModel = ExerciseStub.getModelExercise().authorId, banned: Boolean = false) =
    MpPrincipalModel(
        id = id,
        groups = setOf(
            MpUserGroups.USER,
            MpUserGroups.TEST,
            if (banned) MpUserGroups.BAN else null
        )
            .filterNotNull()
            .toSet()
    )