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

import ru.otus.otuskotlin.workout.mp.transport.models.BaseDebugRequest
import ru.otus.otuskotlin.workout.mp.transport.models.BaseMessage
import ru.otus.otuskotlin.workout.mp.transport.models.BaseRequest
import ru.otus.otuskotlin.workout.mp.transport.models.UpdatableExercise
import ru.otus.otuskotlin.workout.mp.transport.models.UpdateExerciseRequestAllOf

import kotlinx.serialization.*
import kotlinx.serialization.descriptors.*
import kotlinx.serialization.encoding.*

/**
 * Structure for request an update to an existing exercise
 * @param messageType A discriminator contaiting the message class type and used to deserialization
 * @param requestId 
 * @param updateExercise 
 * @param debug 
 */
@Serializable
data class UpdateExerciseRequest (
    /* A discriminator contaiting the message class type and used to deserialization */
    @SerialName(value = "messageType") override val messageType: kotlin.String? = null,
    @SerialName(value = "requestId") val requestId: kotlin.String? = null,
    @SerialName(value = "updateExercise") val updateExercise: UpdatableExercise? = null,
    @SerialName(value = "debug") val debug: BaseDebugRequest? = null
) : BaseMessage

