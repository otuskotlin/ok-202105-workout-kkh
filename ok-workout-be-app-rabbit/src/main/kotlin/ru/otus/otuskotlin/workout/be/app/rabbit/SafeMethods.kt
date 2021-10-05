package ru.otus.otuskotlin.workout.be.app.rabbit

import com.fasterxml.jackson.databind.ObjectMapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.otus.otuskotlin.workout.openapi.models.BaseMessage

suspend fun ObjectMapper.safeReadValue(value: ByteArray): BaseMessage =
    withContext(Dispatchers.IO) { readValue(value, BaseMessage::class.java) }

suspend fun ObjectMapper.safeWriteValueAsBytes(value: BaseMessage): ByteArray =
    withContext(Dispatchers.IO) { writeValueAsBytes(value) }