rootProject.name = "workout"

pluginManagement {
    plugins {
        val kotlinVersion: String by settings
        val openApiVersion: String by settings

        kotlin("jvm") version kotlinVersion
        kotlin("multiplatform") version kotlinVersion
        kotlin("plugin.serialization") version kotlinVersion

        id("org.openapi.generator") version openApiVersion
    }
}

include("ok-m1l1")
include("m1l6")
include("common")
include("m2l2-testing")
include("ok-workout-be-transport-openapi")
include("ok-workout-be-common")
include("ok-workout-be-mapping")
include("ok-workout-be-app-ktor")
include("ok-workout-be-stubs")
include("ok-workout-be-service-openapi")
