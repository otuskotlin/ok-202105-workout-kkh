package ru.otus.otuskotlin.workout.backend.logics.chains.stubs.workout

import handlers.CorChainDsl
import handlers.chain
import handlers.worker
import ru.otus.otuskotlin.workout.backend.logics.workers.noMatchingStubs
import ru.workout.otuskotlin.workout.backend.common.context.BeContext
import ru.workout.otuskotlin.workout.backend.common.context.CorStatus
import ru.workout.otuskotlin.workout.backend.common.models.MpStubCases

internal fun CorChainDsl<BeContext>.workoutReadStub(title: String) = chain {
    this.title = title
    on {
        status == CorStatus.RUNNING &&
                stubCase != MpStubCases.NONE
    }
    worker {
        this.title = "Successful stubCase for READ"
        on {
            stubCase == MpStubCases.SUCCESS
        }
        handle {
            responseWorkout = WorkoutStub.getModelWorkout().copy(idWorkout = requestWorkoutId)
            status = CorStatus.FINISHING
        }
    }

    noMatchingStubs()
}