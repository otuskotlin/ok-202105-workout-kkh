package ru.otus.otuskotlin.workout.backend.logics.workers

import handlers.CorChainDsl
import handlers.worker
import ru.otus.otuskotlin.workout.backend.common.context.BeContext
import ru.otus.otuskotlin.workout.backend.common.models.MpStubCases
import ru.otus.otuskotlin.workout.backend.common.models.MpUserGroups
import ru.otus.otuskotlin.workout.backend.common.models.WorkMode
import ru.otus.otuskotlin.workout.backend.repo.common.exercise.IRepoExercise

internal fun CorChainDsl<BeContext>.chooseDb(title: String) = worker {
    this.title = title
    description = ""
    handle {
        if (principal.groups.contains(MpUserGroups.TEST)) {
            exerciseRepo = config.repoExerciseTest
            return@handle
        }
        exerciseRepo = when (workMode) {
            WorkMode.PROD -> config.repoExerciseProd
            WorkMode.TEST -> config.repoExerciseTest
            WorkMode.STUB -> {
                if (stubCase == MpStubCases.NONE) {
                    stubCase = MpStubCases.SUCCESS
                }
                IRepoExercise.NONE
            }
        }
    }
}