plugins {
    kotlin("jvm")
}

dependencies {
    val coroutinesVersion: String by project
    val ehcacheVersion: String by project

    implementation(kotlin("stdlib"))
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutinesVersion")
    implementation("org.ehcache:ehcache:$ehcacheVersion")

    implementation(project(":ok-workout-be-common"))

    testImplementation(project(":ok-workout-be-repo-test"))
}