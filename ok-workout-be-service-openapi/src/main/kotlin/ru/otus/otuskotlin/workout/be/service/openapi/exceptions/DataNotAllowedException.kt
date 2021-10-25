package ru.otus.otuskotlin.workout.be.service.openapi.exceptions

import ru.otus.otuskotlin.workout.openapi.models.BaseMessage

class DataNotAllowedException(message: String, request: BaseMessage) : Throwable("$message: $request")