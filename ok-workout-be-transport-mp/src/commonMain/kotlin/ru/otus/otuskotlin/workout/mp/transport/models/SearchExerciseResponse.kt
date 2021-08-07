/**
* Workout
* Workout is an app for building a workout program
*
* The version of the OpenAPI document: 0.0.1
* 
*
* NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech).
* https://openapi-generator.tech
* Do not edit the class manually.
*/
package ru.otus.otuskotlin.workout.mp.transport.models

import ru.otus.otuskotlin.workout.mp.transport.models.BaseMessage
import ru.otus.otuskotlin.workout.mp.transport.models.BasePaginatedResponse
import ru.otus.otuskotlin.workout.mp.transport.models.BaseResponse
import ru.otus.otuskotlin.workout.mp.transport.models.RequestError
import ru.otus.otuskotlin.workout.mp.transport.models.ResponseExercise
import ru.otus.otuskotlin.workout.mp.transport.models.SearchExerciseResponseAllOf

import kotlinx.serialization.*
import kotlinx.serialization.descriptors.*
import kotlinx.serialization.encoding.*

/**
 * Structure to respond to a request search of exercise
 * @param messageType A discriminator contaiting the message class type and used to deserialization
 * @param result 
 * @param errors 
 * @param page 
 * @param foundExercises 
 */
@Serializable
data class SearchExerciseResponse (
    /* A discriminator contaiting the message class type and used to deserialization */
    @SerialName(value = "messageType") override val messageType: kotlin.String? = null,
    @SerialName(value = "result") val result: SearchExerciseResponse.Result? = null,
    @SerialName(value = "errors") val errors: kotlin.collections.List<RequestError>? = null,
    @SerialName(value = "page") val page: BasePaginatedResponse? = null,
    @SerialName(value = "foundExercises") val foundExercises: kotlin.collections.List<ResponseExercise>? = null
) : BaseMessage {

    /**
     * 
     * Values: SUCCESS,ERROR
     */
    @Serializable
    enum class Result(val value: kotlin.String) {
        @SerialName(value = "success") SUCCESS("success"),
        @SerialName(value = "error") ERROR("error");
    }
}

