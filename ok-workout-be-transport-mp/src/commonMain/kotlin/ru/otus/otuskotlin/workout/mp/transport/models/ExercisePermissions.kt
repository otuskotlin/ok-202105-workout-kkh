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

/**
* 
* Values: READ,UPDATE,DELETE,SEND
*/

@Serializable
enum class ExercisePermissions(val value: kotlin.String) {


    @SerialName(value = "read")
    READ("read"),

    @SerialName(value = "update")
    UPDATE("update"),

    @SerialName(value = "delete")
    DELETE("delete"),

    @SerialName(value = "send")
    SEND("send");


    /**
    This override toString avoids using the enum var name and uses the actual api value instead.
    In cases the var name and value are different, the client would send incorrect enums to the server.
    **/
    override fun toString(): String {
        return value
    }
}

