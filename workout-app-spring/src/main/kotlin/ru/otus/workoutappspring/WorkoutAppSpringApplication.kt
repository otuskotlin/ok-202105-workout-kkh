package ru.otus.workoutappspring

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class WorkoutAppSpringApplication

fun main(args: Array<String>) {
    runApplication<WorkoutAppSpringApplication>(*args)
}
