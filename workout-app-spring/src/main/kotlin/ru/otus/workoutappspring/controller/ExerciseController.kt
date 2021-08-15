package ru.otus.workoutappspring.controller

import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.otus.otuskotlin.workout.openapi.models.CreateExerciseRequest
import ru.otus.workoutappspring.service.ExerciseService
import ru.workout.otuskotlin.workout.backend.common.context.BeContext
import ru.workout.otuskotlin.workout.backend.mapping.openapi.setQuery
import ru.workout.otuskotlin.workout.backend.mapping.openapi.toCreateExerciseResponse

@RestController
@RequestMapping("/exercise")
class ExerciseController(
    private val exerciseService: ExerciseService
) {

    @PostMapping("create")
    fun createExercise(@RequestBody createExerciseRequest: CreateExerciseRequest) =
        BeContext().setQuery(createExerciseRequest).let {
            exerciseService.create(it)
        }.toCreateExerciseResponse()
}