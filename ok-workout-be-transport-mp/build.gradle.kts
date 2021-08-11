val serializationVersion: String by project

plugins {
    kotlin("multiplatform")
    id("org.openapi.generator")
    kotlin("plugin.serialization")
}

repositories {
    mavenCentral()
}

kotlin {
    jvm {

    }
    js {
        browser()
        nodejs()
    }

    val generationDir = "$buildDir/generated"

    sourceSets {
        val commonMain by getting {
            kotlin.srcDirs("$generationDir/src/commonMain/kotlin")
            dependencies {
                implementation(kotlin("stdlib-common"))
                implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:$serializationVersion")
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test-common"))
                implementation(kotlin("test-annotations-common"))
            }
        }
        val jvmMain by getting {
            kotlin.srcDirs("$generationDir/src/commonMain/kotlin")
            dependencies {
                implementation(kotlin("stdlib-jdk8"))
            }
        }
        val jvmTest by getting {
            dependencies {
                implementation(kotlin("test-junit5"))
            }
        }
        val jsMain by getting {
            kotlin.srcDirs("$generationDir/src/commonMain/kotlin")
            dependencies {
                implementation(kotlin("stdlib-js"))
            }
        }
        val jsTest by getting {
            dependencies {
                implementation(kotlin("test-js"))
            }
        }
    }

    openApiGenerate {
        val openapiGroup = "${rootProject.group}.mp.transport"
        generatorName.set("kotlin")
        library.set("multiplatform")
        outputDir.set(generationDir)
        packageName.set(openapiGroup)
        apiPackage.set("$openapiGroup.api")
        modelPackage.set("$openapiGroup.models")
        invokerPackage.set("$openapiGroup.invoker")
        inputSpec.set("$rootDir/specs/spec-workout-api-v0.0.yaml")

        globalProperties.apply {
            put("models", "")
            put("modelDocs", "false")
        }

        configOptions.set(
            mapOf(
                "dateLibrary" to "string",
                "enumPropertyNaming" to "UPPERCASE",
                "collectionType" to "list"
            )
        )
    }

    tasks {
        compileKotlinMetadata {
            dependsOn(openApiGenerate)
        }
    }

    tasks.withType<Test>().configureEach {
        useJUnitPlatform()
    }
}