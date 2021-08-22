package ru.workout.otuskotlin.workout.backend.common

import java.time.*
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeFormatterBuilder

val pattern = "yyyy-MM-dd HH:mm"

fun String.convertToInstant(): Instant = DateTimeFormatterBuilder()
    .appendPattern(pattern)
    .toFormatter()
    .withZone(ZoneOffset.ofHours(this.takeLast(3).toInt()))
    .parse(this.take(16), Instant::from)

fun Instant.convertToString() = DateTimeFormatter
    .ofPattern(pattern)
    .withZone(ZoneOffset.UTC)
    .format(this) + " UTC"

fun main() {
    val instant = Instant.now()
    println(instant.convertToString())
}