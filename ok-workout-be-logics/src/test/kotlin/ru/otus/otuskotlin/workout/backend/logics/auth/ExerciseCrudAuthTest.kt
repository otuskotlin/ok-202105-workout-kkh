package ru.otus.otuskotlin.workout.backend.logics.auth

import kotlinx.coroutines.runBlocking
import ru.otus.otuskotlin.workout.backend.common.context.BeContext
import ru.otus.otuskotlin.workout.backend.common.context.ContextConfig
import ru.otus.otuskotlin.workout.backend.common.context.CorStatus
import ru.otus.otuskotlin.workout.backend.common.models.*
import ru.otus.otuskotlin.workout.backend.logics.ExerciseCrud
import ru.otus.otuskotlin.workout.backend.logics.helpers.principalUser
import ru.otus.otuskotlin.workout.be.repo.inmemory.models.RepoExerciseInMemory
import kotlin.test.Test
import kotlin.test.assertContains
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class ExerciseCrudAuthTest {

    @Test
    fun createSuccessTest() {
        val repo = RepoExerciseInMemory()
        val crud = ExerciseCrud(
            config = ContextConfig(
                repoExerciseTest = repo
            )
        )
        val context = BeContext(
            workMode = WorkMode.TEST,
            requestExercise = ExerciseStub.getModelExercise { idExercise = ExerciseIdModel.NONE },
            operation = BeContext.MpOperations.CREATE,
            principal = principalUser()
        )
        runBlocking {
            crud.create(context)
        }
        assertEquals(CorStatus.SUCCESS, context.status)
        with(context.responseExercise) {
            assertTrue(idExercise.asString().isNotBlank())
            println(context.responseExercise.permissions)
            assertContains(context.responseExercise.permissions, ExercisePermissions.READ)
            assertContains(context.responseExercise.permissions, ExercisePermissions.UPDATE)
            assertContains(context.responseExercise.permissions, ExercisePermissions.DELETE)
        }
    }

    @Test
    fun readSuccessTest() {
        val repo = RepoExerciseInMemory(
            listOf(ExerciseStub.getModelExercise())
        )
        val crud = ExerciseCrud(
            config = ContextConfig(
                repoExerciseTest = repo
            )
        )
        val context = BeContext(
            workMode = WorkMode.TEST,
            requestExerciseId = ExerciseStub.getModelExercise().idExercise,
            operation = BeContext.MpOperations.READ,
            principal = principalUser()
        )
        runBlocking {
            crud.read(context)
        }
        assertEquals(CorStatus.SUCCESS, context.status)
        with(context.responseExercise) {
            assertTrue(idExercise.asString().isNotBlank())
            println(context.responseExercise.permissions)
            assertContains(context.responseExercise.permissions, ExercisePermissions.READ)
            assertContains(context.responseExercise.permissions, ExercisePermissions.UPDATE)
            assertContains(context.responseExercise.permissions, ExercisePermissions.DELETE)
        }
    }

    @Test
    fun updateSuccessTest() {
        val repo = RepoExerciseInMemory(
            listOf(ExerciseStub.getModelExercise())
        )
        val crud = ExerciseCrud(
            config = ContextConfig(
                repoExerciseTest = repo
            )
        )
        val context = BeContext(
            workMode = WorkMode.TEST,
            requestExercise = ExerciseStub.getModelExercise { title = "Обновленная версия приседаний" },
            operation = BeContext.MpOperations.UPDATE,
            principal = principalUser()
        )
        runBlocking {
            crud.update(context)
        }
        assertEquals(CorStatus.SUCCESS, context.status)
        assertEquals("Обновленная версия приседаний", context.responseExercise.title)
        println(context.responseExercise.permissions)
        assertContains(context.responseExercise.permissions, ExercisePermissions.READ)
        assertContains(context.responseExercise.permissions, ExercisePermissions.UPDATE)
        assertContains(context.responseExercise.permissions, ExercisePermissions.DELETE)
    }

    @Test
    fun deleteSuccessTest() {
        val repo = RepoExerciseInMemory(
            listOf(ExerciseStub.getModelExercise())
        )
        val crud = ExerciseCrud(
            config = ContextConfig(
                repoExerciseTest = repo
            )
        )
        val context = BeContext(
            workMode = WorkMode.TEST,
            requestExerciseId = ExerciseStub.getModelExercise().idExercise,
            operation = BeContext.MpOperations.DELETE,
            principal = principalUser()
        )
        runBlocking {
            crud.delete(context)
        }
        assertEquals(CorStatus.SUCCESS, context.status)
        assertContains(context.responseExercise.permissions, ExercisePermissions.READ)
        assertContains(context.responseExercise.permissions, ExercisePermissions.UPDATE)
        assertContains(context.responseExercise.permissions, ExercisePermissions.DELETE)
    }

    @Test
    fun searchSuccessTest() {
        val repo = RepoExerciseInMemory(
            listOf(
                ExerciseStub.getModelExercise()
            )
        )
        val crud = ExerciseCrud(
            config = ContextConfig(
                repo
            )
        )
        val context = BeContext(
            workMode = WorkMode.TEST,
            requestExerciseFilter = MpExerciseSearchFilter(
                searchStr = "приседания",
            ),
            operation = BeContext.MpOperations.SEARCH,
            principal = principalUser()
        )
        runBlocking { crud.search(context) }
        assertEquals(CorStatus.SUCCESS, context.status)
        context.foundExercises.forEach {
            assertContains(it.permissions, ExercisePermissions.READ)
            assertContains(it.permissions, ExercisePermissions.UPDATE)
            assertContains(it.permissions, ExercisePermissions.DELETE)
        }
    }
}