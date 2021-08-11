package ru.workout.otuskotlin.workout.backend.common.models

data class OneSetModel(
    var performance: MutableList<Performance> = mutableListOf(),
    var status: Status = Status.PLAN,
    var modificationExercise: ModificationExercise = ModificationExercise.NONE
) {
    
    enum class Status {
        PLAN, ACTIVE, DONE, SKIP;
    }

    enum class ModificationExercise {
        NONE, CLUSTER, DROP_SET, SUPER_SET_RIGHT, SUPER_SET_LEFT;
    }
}
