package ru.otus.otuskotlin.workout.be.repo.inmemory.models

import ru.otus.otuskotlin.workout.backend.common.models.*
import java.io.Serializable
import java.time.Instant

data class WorkoutRow(
    val workoutDate: String? = null,
    val duration: Double? = null,
    val recoveryTime: Double? = null,
    val modificationWorkout: String? = null,
    val exercisesBlock: MutableList<ExercisesBlock>? = null,
    val idWorkout: String? = null,
) : Serializable {
    constructor(internal: WorkoutModel) : this(
        workoutDate = internal.workoutDate.toString(),
        duration = internal.duration,
        recoveryTime = internal.recoveryTime,
        modificationWorkout = internal.modificationWorkout.name,
        exercisesBlock = internal.exercisesBlock.map {
            ExercisesBlock(it)
        }.toMutableList(),
        idWorkout = internal.idWorkout.takeIf { it != WorkoutIdModel.NONE }.toString()
    )

    fun toInternal(): WorkoutModel = WorkoutModel(
        workoutDate = Instant.parse(workoutDate),
        duration = duration ?: 0.0,
        recoveryTime = recoveryTime ?: 0.0,
        modificationWorkout = WorkoutModel.ModificationWorkout.valueOf(modificationWorkout ?: "CLASSIC"),
        exercisesBlock = exercisesBlock?.map {
            ExercisesBlockModel(
                exercise = it.exercise.toInternal(),
                sets = it.sets.map { oneSet ->
                    OneSetModel(
                        performance = oneSet.performance.map { performance ->
                            PerformanceModel(
                                weight = performance.weight,
                                measure = PerformanceModel.Measure.valueOf(performance.measure),
                                repetition = performance.repetition
                            )
                        }.toMutableList(),
                        status = OneSetModel.Status.valueOf(oneSet.status),
                        modificationExercise = OneSetModel.ModificationExercise.valueOf(oneSet.modificationExercise)
                    )
                }.toMutableList(),
                modificationBlockExercises = ModificationBlockExercises.valueOf(it.modificationBlockExercises),
            )
        }?.toMutableList() ?: mutableListOf(),
        idWorkout = idWorkout?.let { WorkoutIdModel(it) } ?: WorkoutIdModel.NONE,
    )

    class ExercisesBlock(
        exercisesBlockModel: ExercisesBlockModel
    ) {
        val exercise: ExerciseRow = ExerciseRow(exercisesBlockModel.exercise)
        val sets: MutableList<OneSet> = exercisesBlockModel.sets.map { OneSet(it) }.toMutableList()
        val modificationBlockExercises: String = exercisesBlockModel.modificationBlockExercises.name
    }

    class OneSet(
        oneSetModel: OneSetModel
    ) {
        val performance: MutableList<Performance> = oneSetModel.performance.map { Performance(it) }.toMutableList()
        val status: String = oneSetModel.status.name
        val modificationExercise: String = oneSetModel.modificationExercise.name
    }

    class Performance(
        performanceModel: PerformanceModel
    ) {
        val weight: Double = performanceModel.weight
        val measure: String = performanceModel.measure.name
        val repetition: Int = performanceModel.repetition
    }
}