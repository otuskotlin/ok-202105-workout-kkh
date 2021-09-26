package ru.otus.otuskotlin.workout.controllers

import com.fasterxml.jackson.module.kotlin.readValue
import io.ktor.http.cio.websocket.*
import io.ktor.server.testing.*
import org.junit.Test
import ru.otus.otuskotlin.workout.module
import ru.otus.otuskotlin.workout.Utils
import ru.otus.otuskotlin.workout.openapi.models.*
import kotlin.test.assertIs

class WsControllerTest {
    @Test
    fun wsExerciseTest() {
        withTestApplication({ module(testing = true) }) {
            handleWebSocketConversation("/ws-exercise") { incoming, outgoing ->
                run {
                    val responseFrame = incoming.receive()
                    val response = Utils.mapper.readValue<BaseMessage>(responseFrame.readBytes())
                    assertIs<InitExerciseResponse>(response)
                }

                run {
                    val request = CreateExerciseRequest(
                        requestId = "rID:0001",
                        createExercise = Utils.stubCreatableExercise,
                        debug = Utils.stubDebugSuccess
                    )
                    val requestFrame = Frame.Text(Utils.mapper.writeValueAsString(request))
                    outgoing.send(requestFrame)
                    val responseFrame = incoming.receive()
                    val response = Utils.mapper.readValue<BaseMessage>(responseFrame.readBytes())
                    assertIs<CreateExerciseResponse>(response)
                }
            }
        }
    }

    @Test
    fun wsWorkoutTest() {
        withTestApplication({ module(testing = true) }) {
            handleWebSocketConversation("ws-workout") { incoming, outgoing ->
                run {
                    val responseFrame = incoming.receive()
                    val response = Utils.mapper.readValue<BaseMessage>(responseFrame.readBytes())
                    assertIs<InitWorkoutResponse>(response)
                }

                run {
                    val request = CreateWorkoutRequest(
                        requestId = "rID:0007",
                        debug = Utils.stubDebugSuccess,
                        createWorkout = Utils.stubCreatableWorkout
                    )
                    val requestFrame = Frame.Text(Utils.mapper.writeValueAsString(request))
                    outgoing.send(requestFrame)
                    val responseFrame = incoming.receive()
                    val response = Utils.mapper.readValue<BaseMessage>(responseFrame.readBytes())
                    assertIs<CreateWorkoutResponse>(response)
                }
            }
        }
    }
}