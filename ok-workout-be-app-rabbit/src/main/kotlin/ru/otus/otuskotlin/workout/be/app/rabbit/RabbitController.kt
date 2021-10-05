package ru.otus.otuskotlin.workout.be.app.rabbit

import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.asCoroutineDispatcher
import kotlinx.coroutines.launch
import java.util.concurrent.Executors

class RabbitController(
    val processors: Set<RabbitProcessor>
) {
    private val scope = CoroutineScope(
        Executors.newSingleThreadExecutor().asCoroutineDispatcher() + CoroutineName("main-thread")
    )

    fun start() {
        scope.launch {
            processors.forEach { processor ->
                launch(
                    Executors.newSingleThreadExecutor()
                        .asCoroutineDispatcher() + CoroutineName("thread-${processor.consumerTag}")
                ) {
                    processor.process()
                }
            }
        }
    }
}