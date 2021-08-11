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


import kotlinx.serialization.*
import kotlinx.serialization.descriptors.*
import kotlinx.serialization.encoding.*

/**
 * Base class to requests and responses
 * @param messageType A discriminator contaiting the message class type and used to deserialization
 */

interface BaseMessage {

    /* A discriminator contaiting the message class type and used to deserialization */
    @SerialName(value = "messageType") val messageType: kotlin.String?
}
