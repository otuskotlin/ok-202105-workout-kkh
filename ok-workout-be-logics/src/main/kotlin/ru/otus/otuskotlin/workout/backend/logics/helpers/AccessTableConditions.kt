package ru.otus.otuskotlin.workout.backend.logics.helpers

import ru.otus.otuskotlin.workout.backend.common.context.BeContext
import ru.otus.otuskotlin.workout.backend.common.models.MpPrincipalRelations
import ru.otus.otuskotlin.workout.backend.common.models.MpUserPermissions

data class AccessTableConditions(
    val operation: BeContext.MpOperations,
    val permission: MpUserPermissions,
    val relation: MpPrincipalRelations
)

val accessTable = mapOf(
    // Create
    AccessTableConditions(
        operation = BeContext.MpOperations.CREATE,
        permission = MpUserPermissions.CREATE_AUTHOR,
        relation = MpPrincipalRelations.NONE
    ) to true,

    // Read
    AccessTableConditions(
        operation = BeContext.MpOperations.READ,
        permission = MpUserPermissions.READ_AUTHOR,
        relation = MpPrincipalRelations.AUTHOR
    ) to true,
    AccessTableConditions(
        operation = BeContext.MpOperations.READ,
        permission = MpUserPermissions.READ_PUBLIC,
        relation = MpPrincipalRelations.PUBLIC
    ) to true,

    // Update
    AccessTableConditions(
        operation = BeContext.MpOperations.UPDATE,
        permission = MpUserPermissions.UPDATE_AUTHOR,
        relation = MpPrincipalRelations.AUTHOR
    ) to true,

    // Delete
    AccessTableConditions(
        operation = BeContext.MpOperations.DELETE,
        permission = MpUserPermissions.DELETE_AUTHOR,
        relation = MpPrincipalRelations.AUTHOR
    ) to true
)