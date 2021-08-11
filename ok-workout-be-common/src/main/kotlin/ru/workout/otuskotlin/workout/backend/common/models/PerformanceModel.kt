package ru.workout.otuskotlin.workout.backend.common.models

data class PerformanceModel(
    var weight: Double = 0.0,
    var measure: Measure = Measure.KG,
    var repetition: Int = 0
) {
    enum class Measure {
        KG, LB
    }
}