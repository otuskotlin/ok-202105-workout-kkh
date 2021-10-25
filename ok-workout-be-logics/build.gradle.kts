plugins {
    kotlin("jvm")
}

dependencies {
    val coroutinesVersion: String by project

    implementation(kotlin("stdlib"))

    implementation(project(":ok-workout-mp-common-cor"))
    implementation(project(":ok-workout-mp-cor-validation"))
    implementation(project(":ok-workout-mp-validation"))
    implementation(project(":ok-workout-be-common"))
    implementation(project(":ok-workout-be-stubs"))
    implementation(project(":ok-workout-be-repo-inmemory"))

    testImplementation(kotlin("test"))
    testImplementation(kotlin("test-junit"))
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutinesVersion")
}