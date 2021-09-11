package ru.otus.otuskotlin.workout.backend.logics

import org.junit.Test
import ru.workout.otuskotlin.workout.backend.common.context.BeContext
import ru.workout.otuskotlin.workout.backend.common.models.MpStubCases

class ExerciseCrudValidationTest {

    @Test
    fun createExerciseSuccess() {
        val crud = ExerciseCrud()
        val context = BeContext(
            stubCase = MpStubCases.SUCCESS,
            requestExercise = ExerciseStub.getModelExercise()
        )
    }
}