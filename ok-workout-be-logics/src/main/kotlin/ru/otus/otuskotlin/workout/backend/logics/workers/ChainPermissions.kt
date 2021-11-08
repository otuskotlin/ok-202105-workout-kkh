package ru.otus.otuskotlin.workout.backend.logics.workers

import ICorChainDsl
import handlers.worker
import ru.otus.otuskotlin.workout.backend.common.context.BeContext
import ru.otus.otuskotlin.workout.backend.common.context.CorStatus
import ru.otus.otuskotlin.workout.backend.common.models.MpUserGroups
import ru.otus.otuskotlin.workout.backend.common.models.MpUserPermissions

fun ICorChainDsl<BeContext>.chainPermissions(title: String) = worker {
    this.title = title
    description = "Вычисление прав доступа для групп пользователй"
    on {
        status == CorStatus.RUNNING
    }
    handle {
        val permAdd: Set<MpUserPermissions> = principal.groups.map {
            when (it) {
                MpUserGroups.USER -> setOf(
                    MpUserPermissions.CREATE_AUTHOR,
                    MpUserPermissions.READ_AUTHOR,
                    MpUserPermissions.READ_PUBLIC,
                    MpUserPermissions.UPDATE_AUTHOR,
                    MpUserPermissions.DELETE_AUTHOR,
                    MpUserPermissions.SEARCH_AUTHOR,
                    MpUserPermissions.SEARCH_PUBLIC
                )
                MpUserGroups.ADMIN -> setOf()
                MpUserGroups.TEST -> setOf()
                MpUserGroups.BAN -> setOf()
            }
        }.flatten().toSet()
        val permDel: Set<MpUserPermissions> = principal.groups.map {
            when (it) {
                MpUserGroups.USER -> setOf()
                MpUserGroups.ADMIN -> setOf()
                MpUserGroups.TEST -> setOf()
                MpUserGroups.BAN -> setOf(
                    MpUserPermissions.CREATE_AUTHOR,
                    MpUserPermissions.UPDATE_AUTHOR,
                )
            }
        }.flatten().toSet()
        chainPermissions.addAll(permAdd)
        chainPermissions.removeAll(permDel)
    }
}