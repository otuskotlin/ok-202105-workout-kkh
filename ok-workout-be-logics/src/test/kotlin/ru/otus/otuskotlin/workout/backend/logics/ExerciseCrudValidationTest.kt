package ru.otus.otuskotlin.workout.backend.logics

import kotlinx.coroutines.runBlocking
import org.junit.Test
import ru.otus.otuskotlin.workout.backend.common.context.BeContext
import ru.otus.otuskotlin.workout.backend.common.context.CorStatus
import ru.otus.otuskotlin.workout.backend.common.models.ExerciseIdModel
import ru.otus.otuskotlin.workout.backend.common.models.MpStubCases
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class ExerciseCrudValidationTest {

    @Test
    fun createExerciseSuccess() {
        val crud = ExerciseCrud()
        val context = BeContext(
            requestId = "rID:00111",
            stubCase = MpStubCases.SUCCESS,
            requestExercise = ExerciseStub.getModelExercise() {
                title = "Приседания со штангой"
                description = "Базовое упражнение"
                targetMuscleGroup = mutableListOf("Квадрицепсы")
                synergisticMuscleGroup = mutableListOf(
                    "Большие ягодичные",
                    "Приводящие бедра",
                    "Камбаловидные"
                )
                executionTechnique = "Выполняющий упражнение приседает и затем встаёт, возвращаясь в положение стоя"
            },
            operation = BeContext.MpOperations.CREATE
        )

        runBlocking { crud.create(context) }

        assertEquals(CorStatus.SUCCESS, context.status)
        assertTrue(context.errors.isEmpty())
    }

    @Test
    fun createExerciseFailing() {
        val crud = ExerciseCrud()
        val context = BeContext(
            requestId = "",
            stubCase = MpStubCases.SUCCESS,
            requestExercise = ExerciseStub.getModelExercise() {
                title = ""
                description = ""
                targetMuscleGroup = mutableListOf()
                synergisticMuscleGroup = mutableListOf()
                executionTechnique = ""
            },
            operation = BeContext.MpOperations.CREATE
        )
        runBlocking { crud.create(context) }

        assertEquals(CorStatus.ERROR, context.status)
        assertEquals(5, context.errors.size)
    }

    @Test
    fun readExerciseSuccess() {
        val crud = ExerciseCrud()
        val context = BeContext(
            requestId = "rID:00111",
            stubCase = MpStubCases.SUCCESS,
            requestExercise = ExerciseStub.getModelExercise(),
            requestExerciseId = ExerciseIdModel("eID:00022"),
            operation = BeContext.MpOperations.READ
        )

        runBlocking { crud.read(context) }

        assertEquals(CorStatus.SUCCESS, context.status)
        assertTrue(context.errors.isEmpty())
    }

    @Test
    fun readExerciseFailing() {
        val crud = ExerciseCrud()
        val context = BeContext(
            requestId = "",
            stubCase = MpStubCases.SUCCESS,
            requestExercise = ExerciseStub.getModelExercise(),
            operation = BeContext.MpOperations.READ
        )
        runBlocking { crud.read(context) }

        assertEquals(CorStatus.ERROR, context.status)
        assertEquals(1, context.errors.size)
    }

    @Test
    fun updateExerciseSuccess() {
        val crud = ExerciseCrud()
        val context = BeContext(
            requestId = "rID:00111",
            stubCase = MpStubCases.SUCCESS,
            requestExercise = ExerciseStub.getModelExercise() {
                title = "Приседания со штангой"
                description = "Базовое упражнение"
                targetMuscleGroup = mutableListOf("Квадрицепсы")
                synergisticMuscleGroup = mutableListOf(
                    "Большие ягодичные",
                    "Приводящие бедра",
                    "Камбаловидные"
                )
                executionTechnique = "Выполняющий упражнение приседает и затем встаёт, возвращаясь в положение стоя"
                idExercise = ExerciseIdModel("eID:000111")
            },
            operation = BeContext.MpOperations.UPDATE
        )

        runBlocking { crud.update(context) }

        assertEquals(CorStatus.SUCCESS, context.status)
        assertTrue(context.errors.isEmpty())
    }

    @Test
    fun updateExerciseFailing() {
        val crud = ExerciseCrud()
        val context = BeContext(
            requestId = "",
            stubCase = MpStubCases.SUCCESS,
            requestExercise = ExerciseStub.getModelExercise() {
                title = ""
                description = ""
                targetMuscleGroup = mutableListOf()
                synergisticMuscleGroup = mutableListOf()
                executionTechnique = ""
                idExercise = ExerciseIdModel("")
            },
            operation = BeContext.MpOperations.UPDATE
        )

        println("--" + context.requestExerciseId)
        runBlocking { crud.update(context) }

        println("--" + context.requestExerciseId)

        assertEquals(CorStatus.ERROR, context.status)
        assertEquals(6, context.errors.size)
    }

    @Test
    fun deleteExerciseSuccess() {
        val crud = ExerciseCrud()
        val context = BeContext(
            requestId = "rID:00111",
            requestExerciseId = ExerciseIdModel("eID:00011"),
            stubCase = MpStubCases.SUCCESS,
            requestExercise = ExerciseStub.getModelExercise(),
            operation = BeContext.MpOperations.DELETE
        )

        runBlocking { crud.delete(context) }

        assertEquals(CorStatus.SUCCESS, context.status)
        assertTrue(context.errors.isEmpty())
    }

    @Test
    fun deleteExerciseFailing() {
        val crud = ExerciseCrud()
        val context = BeContext(
            requestId = "",
            stubCase = MpStubCases.SUCCESS,
            requestExercise = ExerciseStub.getModelExercise(),
            operation = BeContext.MpOperations.DELETE
        )
        runBlocking { crud.delete(context) }

        assertEquals(CorStatus.ERROR, context.status)
        assertEquals(1, context.errors.size)
    }

    @Test
    fun searchExerciseSuccess() {
        val crud = ExerciseCrud()
        val context = BeContext(
            requestId = "rID:00111",
            requestSearchExercise = "Подтягивания",
            stubCase = MpStubCases.SUCCESS,
            requestExercise = ExerciseStub.getModelExercise(),
            operation = BeContext.MpOperations.SEARCH
        )

        runBlocking { crud.search(context) }

        assertEquals(CorStatus.SUCCESS, context.status)
        assertTrue(context.errors.isEmpty())
    }

    @Test
    fun searchExerciseFailing() {
        val crud = ExerciseCrud()
        val context = BeContext(
            requestId = "",
            stubCase = MpStubCases.SUCCESS,
            requestExercise = ExerciseStub.getModelExercise(),
            operation = BeContext.MpOperations.SEARCH
        )
        runBlocking { crud.search(context) }

        assertEquals(CorStatus.ERROR, context.status)
        assertEquals(1, context.errors.size)
    }
}