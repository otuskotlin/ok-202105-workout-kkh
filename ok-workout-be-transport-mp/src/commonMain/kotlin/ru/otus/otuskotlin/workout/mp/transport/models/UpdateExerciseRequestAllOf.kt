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
import ru.otus.otuskotlin.workout.mp.transport.models.UpdatableExercise

import kotlinx.serialization.*
import kotlinx.serialization.descriptors.*
import kotlinx.serialization.encoding.*

/**
 * 
 * @param updateExercise 
 * @param debug 
 */
@Serializable
data class UpdateExerciseRequestAllOf (
    @SerialName(value = "updateExercise") val updateExercise: UpdatableExercise? = null,
    @SerialName(value = "debug") val debug: BaseDebugRequest? = null
)

