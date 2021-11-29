plugins {
    kotlin("jvm")
}

dependencies {
    implementation(kotlin("stdlib"))

    implementation(project(":ok-workout-be-common"))
    implementation(project(":ok-workout-be-mapping"))
    implementation(project(":ok-workout-be-transport-openapi"))
    implementation(project(":ok-workout-be-stubs"))
    implementation(project(":ok-workout-be-logics"))
    implementation(project(":ok-workout-be-logging"))

}