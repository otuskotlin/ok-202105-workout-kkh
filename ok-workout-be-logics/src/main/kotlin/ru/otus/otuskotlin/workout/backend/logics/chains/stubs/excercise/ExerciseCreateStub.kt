package ru.otus.otuskotlin.workout.backend.logics.chains.stubs.excercise

import handlers.CorChainDsl
import handlers.chain
import handlers.worker
import ru.workout.otuskotlin.workout.backend.common.context.BeContext
import ru.workout.otuskotlin.workout.backend.common.context.CorStatus
import ru.workout.otuskotlin.workout.backend.common.models.MpStubCases

internal fun CorChainDsl<BeContext>.exerciseCreateStub(title: String) = chain {
    this.title = title
    on {
        status == CorStatus.RUNNING &&
                stubCase != MpStubCases.NONE
    }
    worker {
        this.title = "SUCCESS stubcase worker"
        on {
            stubCase == MpStubCases.SUCCESS
        }
        handle {
            responseExercise = requestExercise.copy(idExercise = ExerciseStub.getModelExercise().idExercise)
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