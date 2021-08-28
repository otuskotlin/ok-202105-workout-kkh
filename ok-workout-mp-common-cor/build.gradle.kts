plugins {
    kotlin("multiplatform")
}

group = "ru.otus.otuskotlin.workout"
version = "0.0.1"

repositories {
    mavenCentral()
}

kotlin {

    js {
        browser()
        nodejs()
    }
    jvm {

    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(kotlin("stdlib-common"))
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test-common"))
                implementation(kotlin("test-annotations-common"))
            }
        }
    }
}