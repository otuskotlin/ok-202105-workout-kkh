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

import ru.otus.otuskotlin.workout.mp.transport.models.CreatableExercise
import ru.otus.otuskotlin.workout.mp.transport.models.UpdatableExerciseAllOf

import kotlinx.serialization.*
import kotlinx.serialization.descriptors.*
import kotlinx.serialization.encoding.*

/**
 * Structure for a data about the declaration in request to update
 * @param title Название упражнения
 * @param description 
 * @param targetMuscleGroup 
 * @param synergisticMuscleGroup 
 * @param executionTechnique 
 * @param id 
 */
@Serializable
data class UpdatableExercise (
    /* Название упражнения */
    @SerialName(value = "title") val title: kotlin.String? = null,
    @SerialName(value = "description") val description: kotlin.String? = null,
    @SerialName(value = "targetMuscleGroup") val targetMuscleGroup: kotlin.collections.List<kotlin.String>? = null,
    @SerialName(value = "synergisticMuscleGroup") val synergisticMuscleGroup: kotlin.collections.List<kotlin.String>? = null,
    @SerialName(value = "executionTechnique") val executionTechnique: kotlin.String? = null,
    @SerialName(value = "id") val id: kotlin.String? = null
)
