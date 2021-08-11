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
import ru.otus.otuskotlin.workout.mp.transport.models.BaseRequest

import kotlinx.serialization.*
import kotlinx.serialization.descriptors.*
import kotlinx.serialization.encoding.*

/**
 * Structure to request the initial state when starting the web client
 * @param messageType A discriminator contaiting the message class type and used to deserialization
 * @param requestId 
 */
@Serializable
data class InitWorkoutRequest (
    /* A discriminator contaiting the message class type and used to deserialization */
    @SerialName(value = "messageType") override val messageType: kotlin.String? = null,
    @SerialName(value = "requestId") val requestId: kotlin.String? = null
) : BaseMessage

