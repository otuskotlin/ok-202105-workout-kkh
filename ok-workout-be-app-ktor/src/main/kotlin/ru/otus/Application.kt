package ru.otus

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.SerializationFeature
import io.ktor.application.*
import io.ktor.features.*
import io.ktor.http.*
import io.ktor.jackson.*
import io.ktor.response.*
import io.ktor.routing.*
import ru.otus.services.ExerciseService

fun main(args: Array<String>): Unit =
    io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused")
@JvmOverloads
fun Application.module(testing: Boolean = false) {

    val exerciseService = ExerciseService()

//    install(Routing)
//    install(DefaultHeaders)
//    install(CORS) {
//        method(HttpMethod.Options)
//        method(HttpMethod.Put)
//        method(HttpMethod.Delete)
//        method(HttpMethod.Patch)
//        header(HttpHeaders.Authorization)
//        header("MyCustomHeader")
//        allowCredentials = true
//    }
    install(ContentNegotiation) {
        jackson {
            disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)

            enable(SerializationFeature.INDENT_OUTPUT)
            writerWithDefaultPrettyPrinter()
        }
    }

//    install(AutoHeadResponse)

    routing {
        get("/") {
            call.respondText("Hello, world!")
        }
        exercise(exerciseService)
    }
}
