package ru.otus.otuskotlin.workout.backend.logics.helpers

import ru.otus.otuskotlin.workout.backend.common.context.BeContext
import ru.otus.otuskotlin.workout.backend.common.models.MpPrincipalRelations
import ru.otus.otuskotlin.workout.backend.common.models.MpUserPermissions

data class AccessTableConditions(
    val operation: BeContext.MpOperations,
    val permissions: MpUserPermissions,
    val relation: MpPrincipalRelations
)

val accessTable = mapOf(
    // Create
    AccessTableConditions(
        operation = BeContext.MpOperations.CREATE,
        permissions = MpUserPermissions.CREATE_OWN,
        relation = MpPrincipalRelations.NONE
    ) to true,

    // Read
    AccessTableConditions(
        operation = BeContext.MpOperations.READ,
        permissions = MpUserPermissions.READ_OWN,
        relation = MpPrincipalRelations.AUTHOR
    ) to true,
    AccessTableConditions(
        operation = BeContext.MpOperations.READ,
        permissions = MpUserPermissions.READ_PUBLIC,
        relation = MpPrincipalRelations.PUBLIC
    ) to true,

    // Update
    AccessTableConditions(
        operation = BeContext.MpOperations.UPDATE,
        permissions = MpUserPermissions.UPDATE_OWN,
        relation = MpPrincipalRelations.AUTHOR
    ) to true,

    // Delete
    AccessTableConditions(
        operation = BeContext.MpOperations.DELETE,
        permissions = MpUserPermissions.DELETE_OWN,
        relation = MpPrincipalRelations.AUTHOR
    ) to true
)