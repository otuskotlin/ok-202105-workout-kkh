package ru.otus.otuskotlin.workout.backend.logics.helpers

import ru.otus.otuskotlin.workout.backend.common.models.ExerciseModel
import ru.otus.otuskotlin.workout.backend.common.models.MpPrincipalModel
import ru.otus.otuskotlin.workout.backend.common.models.MpPrincipalRelations

fun ExerciseModel.resolveRelationsTo(principal: MpPrincipalModel): Set<MpPrincipalRelations> = listOfNotNull(
    MpPrincipalRelations.NONE,
    MpPrincipalRelations.AUTHOR.takeIf { principal.id == authorId },
    MpPrincipalRelations.PUBLIC
).toSet()