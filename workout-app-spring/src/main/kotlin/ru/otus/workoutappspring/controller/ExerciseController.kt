package ru.otus.workoutappspring.controller

import org.springframework.web.bind.annotation.*
import ru.otus.otuskotlin.workout.openapi.models.CreateExerciseRequest
import ru.otus.otuskotlin.workout.openapi.models.ReadExerciseRequest
import ru.otus.otuskotlin.workout.openapi.models.UpdateExerciseRequest
import ru.otus.otuskotlin.workout.openapi.models.UpdateExerciseResponse
import ru.otus.workoutappspring.service.ExerciseService
import ru.workout.otuskotlin.workout.backend.common.context.BeContext
import ru.workout.otuskotlin.workout.backend.mapping.openapi.setQuery
import ru.workout.otuskotlin.workout.backend.mapping.openapi.toCreateExerciseResponse
import ru.workout.otuskotlin.workout.backend.mapping.openapi.toReadExerciseResponse
import ru.workout.otuskotlin.workout.backend.mapping.openapi.toUpdateExerciseResponse

@RestController
@RequestMapping("/exercise")
class ExerciseController(
    private val exerciseService: ExerciseService
) {

    @PostMapping("create")
    fun createExercise(@RequestBody createExerciseRequest: CreateExerciseRequest) =
        BeContext().setQuery(createExerciseRequest).let {
            exerciseService.createExercise(it)
        }.toCreateExerciseResponse()

    @PostMapping("read")
    fun readExercise(@RequestBody readExerciseRequest: ReadExerciseRequest) =
        BeContext().setQuery(readExerciseRequest).let {
            exerciseService.readExercise(it)
        }.toReadExerciseResponse()

    @PostMapping("update")
    fun updateExercise(@RequestBody updateExerciseRequest: UpdateExerciseRequest): UpdateExerciseResponse {
        return BeContext().setQuery(updateExerciseRequest).let {
            exerciseService.updateExercise(it)
        }.toUpdateExerciseResponse()
    }
}