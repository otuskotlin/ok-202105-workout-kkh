package ru.otus.otuskotlin.workout.backend.logics.chains.stubs.excercise

import handlers.CorChainDsl
import handlers.chain
import handlers.worker
import ru.otus.otuskotlin.workout.backend.logics.workers.noMatchingStubs
import ru.otus.otuskotlin.workout.backend.common.context.BeContext
import ru.otus.otuskotlin.workout.backend.common.context.CorStatus
import ru.otus.otuskotlin.workout.backend.common.models.MpStubCases

internal fun CorChainDsl<BeContext>.exerciseSearchStub(title: String) = chain {
    this.title = title
    on {
        status == CorStatus.RUNNING &&
                stubCase != MpStubCases.NONE
    }
    worker {
        this.title = "Successful stubCase for SEARCH"
        on {
            stubCase == MpStubCases.SUCCESS
        }
        handle {
            foundExercises = ExerciseStub.getModelExercises()
            status = CorStatus.FINISHING
        }
    }
    noMatchingStubs()
}