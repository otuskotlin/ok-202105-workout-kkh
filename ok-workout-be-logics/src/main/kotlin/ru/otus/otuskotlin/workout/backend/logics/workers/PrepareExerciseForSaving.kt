package ru.otus.otuskotlin.workout.backend.logics.workers

import ICorChainDsl
import handlers.worker
import ru.otus.otuskotlin.workout.backend.common.context.BeContext
import ru.otus.otuskotlin.workout.backend.common.context.CorStatus

fun ICorChainDsl<BeContext>.prepareExerciseForSaving(title: String) = worker {
    this.title = title
    description = title
    on { status == CorStatus.RUNNING }
    handle {
        with(dbExercise) {
            this.title = requestExercise.title
            description = requestExercise.description
            targetMuscleGroup = requestExercise.targetMuscleGroup
            synergisticMuscleGroup = requestExercise.synergisticMuscleGroup
            executionTechnique = requestExercise.executionTechnique
        }
    }
}