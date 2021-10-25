package ru.otus.otuskotlin.workout.backend.logics.chains.stubs.workout

import handlers.CorChainDsl
import handlers.chain
import handlers.worker
import ru.otus.otuskotlin.workout.backend.logics.workers.noMatchingStubs
import ru.otus.otuskotlin.workout.backend.common.context.BeContext
import ru.otus.otuskotlin.workout.backend.common.context.CorStatus
import ru.otus.otuskotlin.workout.backend.common.models.MpStubCases

internal fun CorChainDsl<BeContext>.workoutCreateStub(title: String) = chain {
    this.title = title
    on {
        status == CorStatus.RUNNING &&
                stubCase != MpStubCases.NONE
    }
    worker {
        this.title = "Successful stubCase for CREATE"
        on {
            stubCase == MpStubCases.SUCCESS
        }
        handle {
            responseWorkout = requestWorkout.copy(
                idWorkout = WorkoutStub.getModelWorkout().idWorkout,
                permissions = WorkoutStub.getModelWorkout().permissions
            )
            status = CorStatus.FINISHING
        }
    }
    noMatchingStubs()
}