package ru.otus.otuskotlin.workout.backend.logics.workers

import ICorChainDsl
import handlers.chain
import handlers.worker
import ru.otus.otuskotlin.workout.backend.common.context.BeContext
import ru.otus.otuskotlin.workout.backend.common.context.CorStatus
import ru.otus.otuskotlin.workout.backend.common.models.ExercisePermissions
import ru.otus.otuskotlin.workout.backend.common.models.MpUserGroups

fun ICorChainDsl<BeContext>.frontPermissions(title: String) = chain {
    this.title = title
    description = "Вычисление разрешений пользователей для фронтенда"
    on { status == CorStatus.RUNNING }

    worker {
        this.title = "Разрешения для пользователя на упражнение, которое он создал"
        description = this.title
        on { responseExercise.authorId == principal.id }
        handle {
            val permAdd: Set<ExercisePermissions> = principal.groups.map {
                when (it) {
                    MpUserGroups.USER -> setOf(
                        ExercisePermissions.READ,
                        ExercisePermissions.UPDATE,
                        ExercisePermissions.DELETE
                    )
                    MpUserGroups.ADMIN -> setOf()
                    MpUserGroups.TEST -> setOf()
                    MpUserGroups.BAN -> setOf()
                }
            }.flatten().toSet()
            val permDel: Set<ExercisePermissions> = principal.groups.map {
                when (it) {
                    MpUserGroups.USER -> setOf()
                    MpUserGroups.ADMIN -> setOf()
                    MpUserGroups.TEST -> setOf()
                    MpUserGroups.BAN -> setOf(
                        ExercisePermissions.UPDATE,
                        ExercisePermissions.DELETE
                    )
                }
            }.flatten().toSet()
            responseExercise.permissions.addAll(permAdd)
            responseExercise.permissions.removeAll(permDel)
        }
    }
    worker {
        this.title = "Разрешения для администратора"
        description = this.title
        on { responseExercise.authorId != principal.id }
        handle {
            val permAdd: Set<ExercisePermissions> = principal.groups.map {
                when (it) {
                    MpUserGroups.USER -> setOf(
                        ExercisePermissions.READ,
                        ExercisePermissions.UPDATE,
                        ExercisePermissions.DELETE
                    )
                    MpUserGroups.ADMIN -> setOf()
                    MpUserGroups.TEST -> setOf()
                    MpUserGroups.BAN -> setOf()
                }
            }.flatten().toSet()
            val permDel: Set<ExercisePermissions> = principal.groups.map {
                when (it) {
                    MpUserGroups.USER -> setOf(
                        ExercisePermissions.UPDATE,
                        ExercisePermissions.DELETE
                    )
                    MpUserGroups.ADMIN -> setOf()
                    MpUserGroups.TEST -> setOf()
                    MpUserGroups.BAN -> setOf(
                        ExercisePermissions.UPDATE,
                        ExercisePermissions.DELETE
                    )
                }
            }.flatten().toSet()
            responseExercise.permissions.addAll(permAdd)
            responseExercise.permissions.removeAll(permDel)
        }
    }
}