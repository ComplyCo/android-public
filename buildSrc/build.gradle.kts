import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    `kotlin-dsl` // Essential for writing build logic in Kotlin
}

repositories {
    google()
    mavenCentral()
    gradlePluginPortal() // For plugins like `kotlin-dsl`
}

dependencies {
    // No dependencies needed, yet
}

// Optional: Configure Kotlin compilation for buildSrc, if needed
tasks.withType<KotlinCompile>().configureEach {
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_17.toString() // Or your desired JVM target
    }
}