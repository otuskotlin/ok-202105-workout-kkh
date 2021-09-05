package ru.otus.otuskotlin.workout.backend.logics.chains.stubs.excercise

import handlers.CorChainDsl
import handlers.chain
import handlers.worker
import ru.otus.otuskotlin.workout.backend.logics.workers.noMatchingStubs
import ru.workout.otuskotlin.workout.backend.common.context.BeContext
import ru.workout.otuskotlin.workout.backend.common.context.CorStatus
import ru.workout.otuskotlin.workout.backend.common.models.MpStubCases

internal fun CorChainDsl<BeContext>.exerciseUpdateStub(title: String) = chain {
    this.title = title
    on {
        status == CorStatus.RUNNING &&
                stubCase != MpStubCases.NONE
    }
    worker {
        this.title = "Successful stubCase for UPDATE"
        on {
            stubCase == MpStubCases.SUCCESS
        }
        handle {
            responseExercise = ExerciseStub.getModelExercise().copy(idExercise = requestExercise.idExercise)
            status = CorStatus.FINISHING
        }
    }
    noMatchingStubs()
}