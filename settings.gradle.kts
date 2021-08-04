rootProject.name = "workout"

pluginManagement {
    plugins {
        val kotlinVersion: String by settings
        val openApiVersion: String by settings

        kotlin("jvm") version kotlinVersion
        kotlin("multiplatform") version kotlinVersion

        id("org.openapi.generator") version openApiVersion
    }
}

include("ok-m1l1")
include("m1l6")
include("common")
include("m2l2-testing")
include("ok-workout-be-transport-openapi")
