package ru.otus.otuskotlin.workout.backend.logics

import kotlinx.coroutines.runBlocking
import org.junit.Test
import ru.workout.otuskotlin.workout.backend.common.context.BeContext
import ru.workout.otuskotlin.workout.backend.common.context.CorStatus
import ru.workout.otuskotlin.workout.backend.common.models.MpStubCases
import kotlin.test.assertEquals

class ExerciseCrudTest {

    @Test
    fun exerciseCreateSuccess() {
        val crud = ExerciseCrud()
        val context = BeContext(
            requestExercise = ExerciseStub.getModelExercise(),
            operation = BeContext.MpOperations.CREATE,
            stubCase = MpStubCases.SUCCESS
        )
        runBlocking {
            crud.create(context)
            val expected = ExerciseStub.getModelExercise()
            assertEquals(CorStatus.SUCCESS, context.status)
            with(context.responseExercise) {
                assertEquals(expected.idExercise, idExercise)
                assertEquals(expected.description, description)
                assertEquals(expected.title, title)
            }
        }
    }
}