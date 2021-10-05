plugins {
    kotlin("jvm")
}

dependencies {
    val kotlinStdLibVersion: String by project
    implementation("org.jetbrains.kotlin:kotlin-stdlib:$kotlinStdLibVersion")
}