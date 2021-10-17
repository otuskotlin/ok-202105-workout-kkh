package ru.otus.otuskotlin.workout.backend.logics.chains.stubs.workout

import handlers.CorChainDsl
import handlers.chain
import handlers.worker
import ru.otus.otuskotlin.workout.backend.logics.workers.noMatchingStubs
import ru.otus.otuskotlin.workout.backend.common.context.BeContext
import ru.otus.otuskotlin.workout.backend.common.context.CorStatus
import ru.otus.otuskotlin.workout.backend.common.models.MpStubCases

fun CorChainDsl<BeContext>.workoutSearchStub(title: String) = chain {
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
            foundWorkouts = WorkoutStub.getWorkouts().also { workouts ->
                workouts.first().also { workout ->
                    workout.exercisesBlock.first().also { exercisesBlock ->
                        exercisesBlock.exercise.targetMuscleGroup =
                            mutableListOf(requestSearchWorkout.searchMuscleGroup)
                    }
                    workout.workoutDate = requestSearchWorkout.workoutDate
                }
            }
            status = CorStatus.FINISHING
        }
    }
    noMatchingStubs()
}