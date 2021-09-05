package ru.otus.otuskotlin.workout.backend.logics.chains.stubs.excercise

import handlers.CorChainDsl
import handlers.chain
import handlers.worker
import ru.workout.otuskotlin.workout.backend.common.context.BeContext
import ru.workout.otuskotlin.workout.backend.common.context.CorStatus
import ru.workout.otuskotlin.workout.backend.common.models.MpStubCases

internal fun CorChainDsl<BeContext>.exerciseReadStub(title: String) = chain {
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
            responseExercise = ExerciseStub.getModelExercise().copy(idExercise = requestExerciseId)
            status = CorStatus.FINISHING
        }
    }
    worker {
        this.title = "No matching stubCase"
        on {
            status == CorStatus.RUNNING
        }
        handle {
            status = CorStatus.FAILING
            addError(
                e = Exception("No matching worker for stubCase: $stubCase")
            )
        }
    }
}