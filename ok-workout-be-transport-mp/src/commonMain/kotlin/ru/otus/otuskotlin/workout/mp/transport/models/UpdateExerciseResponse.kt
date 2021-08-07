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
import ru.otus.otuskotlin.workout.mp.transport.models.BaseResponse
import ru.otus.otuskotlin.workout.mp.transport.models.RequestError
import ru.otus.otuskotlin.workout.mp.transport.models.ResponseExercise
import ru.otus.otuskotlin.workout.mp.transport.models.UpdateExerciseResponseAllOf

import kotlinx.serialization.*
import kotlinx.serialization.descriptors.*
import kotlinx.serialization.encoding.*

/**
 * The structure to respond to a request with an exercise information
 * @param messageType A discriminator contaiting the message class type and used to deserialization
 * @param result 
 * @param errors 
 * @param updateExercise 
 */
@Serializable
data class UpdateExerciseResponse (
    /* A discriminator contaiting the message class type and used to deserialization */
    @SerialName(value = "messageType") override val messageType: kotlin.String? = null,
    @SerialName(value = "result") val result: UpdateExerciseResponse.Result? = null,
    @SerialName(value = "errors") val errors: kotlin.collections.List<RequestError>? = null,
    @SerialName(value = "updateExercise") val updateExercise: ResponseExercise? = null
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

