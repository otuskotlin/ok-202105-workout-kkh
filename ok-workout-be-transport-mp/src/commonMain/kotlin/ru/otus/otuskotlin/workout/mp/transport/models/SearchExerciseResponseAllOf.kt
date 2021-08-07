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

import ru.otus.otuskotlin.workout.mp.transport.models.BasePaginatedResponse
import ru.otus.otuskotlin.workout.mp.transport.models.ResponseExercise

import kotlinx.serialization.*
import kotlinx.serialization.descriptors.*
import kotlinx.serialization.encoding.*

/**
 * 
 * @param page 
 * @param foundExercises 
 */
@Serializable
data class SearchExerciseResponseAllOf (
    @SerialName(value = "page") val page: BasePaginatedResponse? = null,
    @SerialName(value = "foundExercises") val foundExercises: kotlin.collections.List<ResponseExercise>? = null
)

