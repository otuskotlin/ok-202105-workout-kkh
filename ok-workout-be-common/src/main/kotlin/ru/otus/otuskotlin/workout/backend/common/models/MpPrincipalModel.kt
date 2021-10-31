package ru.otus.otuskotlin.workout.backend.common.models

data class MpPrincipalModel(
    val fname: String = "",
    val mname: String = "",
    val lname: String = "",
    val groups: Set<MpUserGroups> = emptySet()
) {
    companion object {
        val NONE = MpPrincipalModel()
    }
}
