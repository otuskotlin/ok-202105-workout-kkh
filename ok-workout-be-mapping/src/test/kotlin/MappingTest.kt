import ru.otus.otuskotlin.workout.openapi.models.*
import ru.workout.otuskotlin.workout.backend.common.context.BeContext
import ru.workout.otuskotlin.workout.backend.common.models.*
import ru.workout.otuskotlin.workout.backend.common.models.ExercisePermissions
import ru.workout.otuskotlin.workout.backend.mapping.openapi.*
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class MappingTest {

    private val workoutDate: String = "2021-08-01"

    private val exercise = ExerciseModel(
        title = "Приседания со штангой",
        description = "Базовое упражнение",
        targetMuscleGroup = mutableListOf("Квадрицепсы"),
        synergisticMuscleGroup = mutableListOf("Большие ягодичные", "Приводящие бедра", "Камбаловидные"),
        executionTechnique = "Выполняющий упражнение приседает и затем встаёт, возвращаясь в положение стоя",
        idExercise = ExerciseIdModel("eID:0001"),
        permissions = mutableSetOf(ExercisePermissions.READ)
    )

    private val performance = PerformanceModel(
        weight = 80.0,
        measure = PerformanceModel.Measure.KG,
        repetition = 10
    )

    private val oneSet = OneSetModel(
        performance = mutableListOf(performance),
        status = OneSetModel.Status.DONE,
        modificationExercise = OneSetModel.ModificationExercise.NONE
    )

    private val exercisesBlock = ExercisesBlockModel(
        exercise = exercise,
        sets = mutableListOf(oneSet),
        modificationBlockExercises = ModificationBlockExercises.NONE
    )

    private val workout = WorkoutModel(
        workoutDate = "2021-08-02",
        duration = 100.0,
        recoveryTime = 90.0,
        modificationWorkout = WorkoutModel.ModificationWorkout.CLASSIC,
        exercisesBlock = mutableListOf(exercisesBlock),
        idWorkout = WorkoutIdModel("wID:0001"),
        permissions = mutableSetOf(ExercisePermissions.CREATE, ExercisePermissions.READ)
    )

    private val beContext = BeContext(
        requestId = "rID:0001",
        responseExercise = exercise,
        responseWorkout = workout,
        foundExercises = mutableListOf(exercise),
        foundWorkouts = mutableListOf(workout),
        responseExercises = mutableListOf(exercise)
    )

    private val readExerciseRequest = ReadExerciseRequest(
        requestId = "rID:0001",
        readExerciseId = "eID:0001",
    )

    private val updateExerciseRequest = UpdateExerciseRequest(
        requestId = "rID:0001",
        updateExercise = UpdatableExercise(
            title = "Приседания со штангой",
            description = "Базовое упражнение",
            targetMuscleGroup = mutableListOf("Квадрицепсы"),
            synergisticMuscleGroup = mutableListOf("Большие ягодичные", "Приводящие бедра", "Камбаловидные"),
            executionTechnique = "Выполняющий упражнение приседает и затем встаёт, возвращаясь в положение стоя",
            id = "eID:0001"
        )
    )

    private val deleteExerciseRequest = DeleteExerciseRequest(
        requestId = "rID:0001",
        deleteExerciseId = "eID:0001"
    )

    private val searchExerciseRequest = SearchExerciseRequest(
        requestId = "rID:0001",
        search = "Приседания со штангой"
    )

    @Test
    fun initExerciseRequest() {
        beContext.setQuery(
            InitExerciseRequest(
                requestId = "rID:0001"
            )
        )
        println(beContext)
        assertEquals("rID:0001", beContext.requestId)
    }

    @Test
    fun createExerciseRequest() {
        beContext.setQuery(
            CreateExerciseRequest(
                requestId = "rID:0001",
                createExercise = CreatableExercise(
                    title = "Приседания со штангой",
                    description = "Базовое упражнение",
                    targetMuscleGroup = mutableListOf("Квадрицепсы"),
                    synergisticMuscleGroup = mutableListOf("Большие ягодичные", "Приводящие бедра", "Камбаловидные"),
                    executionTechnique = "Выполняющий упражнение приседает и затем встаёт, возвращаясь в положение стоя"
                )
            )
        )
        println(beContext)
        assertEquals("rID:0001", beContext.requestId)
        assertTrue(beContext.requestExercise.title.isNotBlank())
        assertTrue(beContext.requestExercise.description.isNotBlank())
        assertEquals(1, beContext.requestExercise.targetMuscleGroup.size)
        assertTrue(beContext.requestExercise.targetMuscleGroup.isNotEmpty())
        assertTrue(beContext.requestExercise.synergisticMuscleGroup.isNotEmpty())
        assertTrue(beContext.requestExercise.executionTechnique.isNotBlank())
    }

    @Test
    fun readExerciseRequest() {
        beContext.setQuery(readExerciseRequest)
        println(beContext)
        assertEquals("rID:0001", beContext.requestId)
        assertEquals(ExerciseIdModel("eID:0001"), beContext.requestExerciseId)
    }

    @Test
    fun updateExerciseRequest() {
        beContext.setQuery(updateExerciseRequest)
        println(beContext)
        assertEquals("rID:0001", beContext.requestId)
        assertTrue(beContext.requestExercise.title.isNotBlank())
        assertTrue(beContext.requestExercise.description.isNotBlank())
        assertEquals(1, beContext.requestExercise.targetMuscleGroup.size)
        assertTrue(beContext.requestExercise.targetMuscleGroup.isNotEmpty())
        assertTrue(beContext.requestExercise.synergisticMuscleGroup.isNotEmpty())
        assertTrue(beContext.requestExercise.executionTechnique.isNotBlank())
        assertEquals(ExerciseIdModel("eID:0001"), beContext.requestExercise.idExercise)
    }

    @Test
    fun deleteExerciseRequest() {
        beContext.setQuery(deleteExerciseRequest)
        println(beContext)
        assertEquals("rID:0001", beContext.requestId)
        assertEquals(ExerciseIdModel("eID:0001"), beContext.requestExerciseId)
    }

    @Test
    fun searchExerciseRequest() {
        beContext.setQuery(searchExerciseRequest)
        println(beContext)
        assertEquals("rID:0001", beContext.requestId)
        assertTrue(beContext.requestSearchExercise.isNotEmpty())
    }

    @Test
    fun initWorkoutRequest() {
        beContext.setQuery(
            InitWorkoutRequest(
                requestId = "rID:0001",
            )
        )
        println(beContext)
        assertEquals("rID:0001", beContext.requestId)
    }

    @Test
    fun createWorkoutRequest() {
        beContext.setQuery(
            CreateWorkoutRequest(
                requestId = "rID:0001",
                createWorkout = CreatableWorkout(
                    date = "2021-09-09",
                    duration = 120.0,
                    recoveryTime = 90.0,
                    modificationWorkout = CreatableWorkout.ModificationWorkout.CLASSIC,
                    exercisesBlock = mutableListOf(
                        ExercisesBlock(
                            ResponseExercise(
                                title = "Приседания со штангой",
                                description = "Базовое упражнение",
                                targetMuscleGroup = mutableListOf("Квадрицепсы"),
                                synergisticMuscleGroup = mutableListOf(
                                    "Большие ягодичные",
                                    "Приводящие бедра",
                                    "Камбаловидные"
                                ),
                                executionTechnique = "Выполняющий упражнение приседает и затем встаёт, возвращаясь в положение стоя",
                                id = "eID:0001",
                                permissions = mutableSetOf(Permissions.READ, Permissions.SEND)
                            )
                        )
                    )
                )
            )
        )
        println(beContext)
        assertEquals("2021-09-09", beContext.requestWorkout.workoutDate)
        assertTrue(beContext.requestWorkout.duration > 0)
        assertTrue(beContext.requestWorkout.recoveryTime > 0)
        assertEquals(WorkoutModel.ModificationWorkout.CLASSIC, beContext.requestWorkout.modificationWorkout)
        assertTrue(beContext.requestWorkout.exercisesBlock.isNotEmpty())
    }

    @Test
    fun readWorkoutRequest() {
        beContext.setQuery(
            ReadWorkoutRequest(
                requestId = "rID:0001",
                readWorkoutId = "wID:0001",
            )
        )
        println(beContext)
        assertEquals("rID:0001", beContext.requestId)
        assertEquals(WorkoutIdModel("wID:0001"), beContext.requestWorkoutId)
    }

    @Test
    fun updateWorkoutRequest() {
        beContext.setQuery(
            UpdateWorkoutRequest(
                requestId = "rID:0001",
                updateWorkout = UpdatableWorkout(
                    date = "2021-09-09",
                    duration = 120.0,
                    recoveryTime = 90.0,
                    modificationWorkout = UpdatableWorkout.ModificationWorkout.CLASSIC,
                    exercisesBlock = mutableListOf(
                        ExercisesBlock(
                            ResponseExercise(
                                title = "Приседания со штангой",
                                description = "Базовое упражнение",
                                targetMuscleGroup = mutableListOf("Квадрицепсы"),
                                synergisticMuscleGroup = mutableListOf(
                                    "Большие ягодичные",
                                    "Приводящие бедра",
                                    "Камбаловидные"
                                ),
                                executionTechnique = "Выполняющий упражнение приседает и затем встаёт, возвращаясь в положение стоя",
                                id = "eID:0001",
                                permissions = mutableSetOf(Permissions.READ, Permissions.SEND)
                            )
                        )
                    )
                )
            )
        )
        println(beContext)
        assertEquals("rID:0001", beContext.requestId)
        assertTrue(beContext.requestWorkout.duration > 0)
        assertTrue(beContext.requestWorkout.recoveryTime > 0)
        assertEquals(WorkoutModel.ModificationWorkout.CLASSIC, beContext.requestWorkout.modificationWorkout)
        assertTrue(beContext.requestWorkout.exercisesBlock.isNotEmpty())
    }

    @Test
    fun deleteWorkoutRequest() {
        beContext.setQuery(
            DeleteWorkoutRequest(
                requestId = "rID:0001",
                deleteWorkoutId = "wID:0001"
            )
        )
        assertEquals("rID:0001", beContext.requestId)
        assertEquals(WorkoutIdModel("wID:0001"), beContext.requestWorkoutId)
    }

    @Test
    fun searchWorkoutRequest() {
        beContext.setQuery(
            SearchWorkoutRequest(
                date = workoutDate,
                searchMuscleGroup = "Квадрицепсы",
                searchExercise = "Приседания",
            )
        )

        assertTrue("date must be $workoutDate") {
            beContext.requestSearchWorkout.workoutDate == workoutDate
        }
    }

    @Test
    fun initExerciseResponseTest() {
        val response = beContext.toInitExerciseResponse()
        println(response)
        assertEquals("rID:0001", response.requestId)
        assertEquals(InitExerciseResponse.Result.SUCCESS, response.result)
        assertTrue(response.errors.isNullOrEmpty())
    }

    @Test
    fun createExerciseResponseTest() {
        val response = beContext.toCreateExerciseResponse()
        println(response)
        assertEquals("rID:0001", response.requestId)
        assertEquals(CreateExerciseResponse.Result.SUCCESS, response.result)
        assertTrue(response.errors.isNullOrEmpty())
        assertTrue(response.createdExercise != null)
        assertTrue(response.createdExercise?.title?.isNotBlank() ?: false)
        assertTrue(response.createdExercise?.description?.isNotBlank() ?: false)
        assertTrue(response.createdExercise?.targetMuscleGroup?.isNotEmpty() ?: false)
        assertTrue(response.createdExercise?.synergisticMuscleGroup?.isNotEmpty() ?: false)
        assertTrue(response.createdExercise?.executionTechnique?.isNotBlank() ?: false)
        assertEquals("eID:0001", response.createdExercise?.id)
        assertTrue(response.createdExercise?.permissions?.contains(Permissions.READ) ?: false)
    }

    @Test
    fun readExerciseResponseTest() {
        val response = beContext.toReadExerciseResponse()
        println(response)
        assertEquals("rID:0001", response.requestId)
        assertEquals(ReadExerciseResponse.Result.SUCCESS, response.result)
        assertTrue(response.errors.isNullOrEmpty())
        assertTrue(response.readExercise != null)
        assertTrue(response.readExercise?.title?.isNotBlank() ?: false)
        assertTrue(response.readExercise?.description?.isNotBlank() ?: false)
        assertTrue(response.readExercise?.targetMuscleGroup?.isNotEmpty() ?: false)
        assertTrue(response.readExercise?.synergisticMuscleGroup?.isNotEmpty() ?: false)
        assertTrue(response.readExercise?.executionTechnique?.isNotBlank() ?: false)
        assertEquals("eID:0001", response.readExercise?.id)
        assertTrue(response.readExercise?.permissions?.contains(Permissions.READ) ?: false)
    }

    @Test
    fun updateExerciseResponseTest() {
        val response = beContext.toUpdateExerciseResponse()
        println(response)
        assertEquals("rID:0001", response.requestId)
        assertEquals(UpdateExerciseResponse.Result.SUCCESS, response.result)
        assertTrue(response.errors.isNullOrEmpty())
        assertTrue(response.updateExercise != null)
        assertTrue(response.updateExercise?.title?.isNotBlank() ?: false)
        assertTrue(response.updateExercise?.description?.isNotBlank() ?: false)
        assertTrue(response.updateExercise?.targetMuscleGroup?.isNotEmpty() ?: false)
        assertTrue(response.updateExercise?.synergisticMuscleGroup?.isNotEmpty() ?: false)
        assertTrue(response.updateExercise?.executionTechnique?.isNotBlank() ?: false)
        assertEquals("eID:0001", response.updateExercise?.id)
        assertTrue(response.updateExercise?.permissions?.contains(Permissions.READ) ?: false)
    }

    @Test
    fun deleteExerciseResponseTest() {
        val response = beContext.toDeleteExerciseResponse()
        println(response)
        assertEquals("rID:0001", response.requestId)
        assertEquals(DeleteExerciseResponse.Result.SUCCESS, response.result)
        assertTrue(response.errors.isNullOrEmpty())
        assertTrue(response.deleteExercise != null)
        assertTrue(response.deleteExercise?.title?.isNotBlank() ?: false)
        assertTrue(response.deleteExercise?.description?.isNotBlank() ?: false)
        assertTrue(response.deleteExercise?.targetMuscleGroup?.isNotEmpty() ?: false)
        assertTrue(response.deleteExercise?.synergisticMuscleGroup?.isNotEmpty() ?: false)
        assertTrue(response.deleteExercise?.executionTechnique?.isNotBlank() ?: false)
        assertEquals("eID:0001", response.deleteExercise?.id)
        assertTrue(response.deleteExercise?.permissions?.contains(Permissions.READ) ?: false)
    }

    @Test
    fun searchExerciseResponseTest() {
        val response = beContext.toSearchExerciseResponse()
        println(response)
        assertEquals("rID:0001", response.requestId)
        assertEquals(SearchExerciseResponse.Result.SUCCESS, response.result)
        assertTrue(response.errors.isNullOrEmpty())
        assertTrue(response.foundExercises?.isNotEmpty() ?: false)
    }

    @Test
    fun initWorkoutResponseTest() {
        val response = beContext.toInitWorkoutResponse()
        println(response)
        assertEquals("rID:0001", response.requestId)
        assertEquals(InitWorkoutResponse.Result.SUCCESS, response.result)
        assertTrue(response.errors.isNullOrEmpty())
    }

    @Test
    fun createWorkoutResponseTest() {
        val response = beContext.toCreateWorkoutResponse()
        println(response)
        assertEquals("rID:0001", response.requestId)
        assertEquals(CreateWorkoutResponse.Result.SUCCESS, response.result)
        assertTrue(response.errors.isNullOrEmpty())
        assertEquals("2021-08-02", response.createdWorkout?.date)
        assertEquals(100.0, response.createdWorkout?.duration)
        assertEquals(90.0, response.createdWorkout?.recoveryTime)
        assertEquals(ResponseWorkout.ModificationWorkout.CLASSIC, response.createdWorkout?.modificationWorkout)
        assertTrue(response.createdWorkout?.exercisesBlock?.isNotEmpty() ?: false)
        assertTrue(response.createdWorkout?.exercisesBlock?.first()?.exercise?.title?.isNotBlank() ?: false)
        assertTrue(response.createdWorkout?.exercisesBlock?.first()?.exercise?.description?.isNotBlank() ?: false)
        assertTrue(response.createdWorkout?.exercisesBlock?.first()?.exercise?.targetMuscleGroup?.isNotEmpty() ?: false)
        assertTrue(
            response.createdWorkout?.exercisesBlock?.first()?.exercise?.synergisticMuscleGroup?.isNotEmpty() ?: false
        )
        assertTrue(
            response.createdWorkout?.exercisesBlock?.first()?.exercise?.executionTechnique?.isNotBlank() ?: false
        )
        assertEquals("eID:0001", response.createdWorkout?.exercisesBlock?.first()?.exercise?.id)
        assertTrue(
            response.createdWorkout?.exercisesBlock?.first()?.exercise?.permissions?.contains(Permissions.READ)
                ?: false
        )
        assertEquals("wID:0001", response.createdWorkout?.id)
        assertTrue(
            response.createdWorkout?.permissions?.containsAll(listOf(Permissions.CREATE, Permissions.READ)) ?: false
        )
    }

    @Test
    fun readWorkoutResponseTest() {
        val response = beContext.toReadWorkoutResponse()
        println(response)
        assertEquals("rID:0001", response.requestId)
        assertEquals(ReadWorkoutResponse.Result.SUCCESS, response.result)
        assertTrue(response.errors.isNullOrEmpty())
        assertEquals("2021-08-02", response.readWorkout?.date)
        assertEquals(100.0, response.readWorkout?.duration)
        assertEquals(90.0, response.readWorkout?.recoveryTime)
        assertEquals(ResponseWorkout.ModificationWorkout.CLASSIC, response.readWorkout?.modificationWorkout)
        assertTrue(response.readWorkout?.exercisesBlock?.isNotEmpty() ?: false)
        assertTrue(response.readWorkout?.exercisesBlock?.first()?.exercise?.title?.isNotBlank() ?: false)
        assertTrue(response.readWorkout?.exercisesBlock?.first()?.exercise?.description?.isNotBlank() ?: false)
        assertTrue(response.readWorkout?.exercisesBlock?.first()?.exercise?.targetMuscleGroup?.isNotEmpty() ?: false)
        assertTrue(
            response.readWorkout?.exercisesBlock?.first()?.exercise?.synergisticMuscleGroup?.isNotEmpty() ?: false
        )
        assertTrue(
            response.readWorkout?.exercisesBlock?.first()?.exercise?.executionTechnique?.isNotBlank() ?: false
        )
        assertEquals("eID:0001", response.readWorkout?.exercisesBlock?.first()?.exercise?.id)
        assertTrue(
            response.readWorkout?.exercisesBlock?.first()?.exercise?.permissions?.contains(Permissions.READ)
                ?: false
        )
        assertEquals("wID:0001", response.readWorkout?.id)
        assertTrue(
            response.readWorkout?.permissions?.containsAll(listOf(Permissions.CREATE, Permissions.READ)) ?: false
        )
    }

    @Test
    fun updateWorkoutResponseTest() {
        val response = beContext.toUpdateWorkoutResponse()
        println(response)
        assertEquals("rID:0001", response.requestId)
        assertEquals(UpdateWorkoutResponse.Result.SUCCESS, response.result)
        assertTrue(response.errors.isNullOrEmpty())
        assertEquals("2021-08-02", response.updateWorkout?.date)
        assertEquals(100.0, response.updateWorkout?.duration)
        assertEquals(90.0, response.updateWorkout?.recoveryTime)
        assertEquals(ResponseWorkout.ModificationWorkout.CLASSIC, response.updateWorkout?.modificationWorkout)
        assertTrue(response.updateWorkout?.exercisesBlock?.isNotEmpty() ?: false)
        assertTrue(response.updateWorkout?.exercisesBlock?.first()?.exercise?.title?.isNotBlank() ?: false)
        assertTrue(response.updateWorkout?.exercisesBlock?.first()?.exercise?.description?.isNotBlank() ?: false)
        assertTrue(response.updateWorkout?.exercisesBlock?.first()?.exercise?.targetMuscleGroup?.isNotEmpty() ?: false)
        assertTrue(
            response.updateWorkout?.exercisesBlock?.first()?.exercise?.synergisticMuscleGroup?.isNotEmpty() ?: false
        )
        assertTrue(response.updateWorkout?.exercisesBlock?.first()?.exercise?.executionTechnique?.isNotBlank() ?: false)
        assertEquals("eID:0001", response.updateWorkout?.exercisesBlock?.first()?.exercise?.id)
        assertTrue(
            response.updateWorkout?.exercisesBlock?.first()?.exercise?.permissions?.contains(Permissions.READ)
                ?: false
        )
        assertEquals("wID:0001", response.updateWorkout?.id)
        assertTrue(
            response.updateWorkout?.permissions?.containsAll(listOf(Permissions.CREATE, Permissions.READ)) ?: false
        )
    }

    @Test
    fun deleteWorkoutResponseTest() {
        val response = beContext.toDeleteWorkoutResponse()
        println(response)
        assertEquals("rID:0001", response.requestId)
        assertEquals(DeleteWorkoutResponse.Result.SUCCESS, response.result)
        assertTrue(response.errors.isNullOrEmpty())
        assertEquals("2021-08-02", response.deleteWorkout?.date)
        assertEquals(100.0, response.deleteWorkout?.duration)
        assertEquals(90.0, response.deleteWorkout?.recoveryTime)
        assertEquals(ResponseWorkout.ModificationWorkout.CLASSIC, response.deleteWorkout?.modificationWorkout)
        assertTrue(response.deleteWorkout?.exercisesBlock?.isNotEmpty() ?: false)
        assertTrue(response.deleteWorkout?.exercisesBlock?.first()?.exercise?.title?.isNotBlank() ?: false)
        assertTrue(response.deleteWorkout?.exercisesBlock?.first()?.exercise?.description?.isNotBlank() ?: false)
        assertTrue(response.deleteWorkout?.exercisesBlock?.first()?.exercise?.targetMuscleGroup?.isNotEmpty() ?: false)
        assertTrue(
            response.deleteWorkout?.exercisesBlock?.first()?.exercise?.synergisticMuscleGroup?.isNotEmpty() ?: false
        )
        assertTrue(response.deleteWorkout?.exercisesBlock?.first()?.exercise?.executionTechnique?.isNotBlank() ?: false)
        assertEquals("eID:0001", response.deleteWorkout?.exercisesBlock?.first()?.exercise?.id)
        assertTrue(
            response.deleteWorkout?.exercisesBlock?.first()?.exercise?.permissions?.contains(Permissions.READ)
                ?: false
        )
        assertEquals("wID:0001", response.deleteWorkout?.id)
        assertTrue(
            response.deleteWorkout?.permissions?.containsAll(listOf(Permissions.CREATE, Permissions.READ)) ?: false
        )
    }

    @Test
    fun searchWorkoutResponseTest() {
        val response = beContext.toSearchWorkoutResponse()
        println(response)
        assertEquals("rID:0001", response.requestId)
        assertEquals(SearchWorkoutResponse.Result.SUCCESS, response.result)
        assertTrue(response.errors.isNullOrEmpty())
        assertTrue(response.foundWorkouts?.isNotEmpty() ?: false)
    }

    @Test
    fun chainOfExercise() {
        val response = beContext.toChainOfExercises()
        println(response)
        assertEquals("rID:0001", response.requestId)
        assertEquals(ChainOfExerciseResponse.Result.SUCCESS, response.result)
        assertTrue(response.errors.isNullOrEmpty())
        assertTrue(response.chainOfExercise?.isNotEmpty() ?: false)
    }
}
