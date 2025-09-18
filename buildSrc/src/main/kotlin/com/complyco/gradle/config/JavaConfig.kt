package com.complyco.gradle.config

import org.gradle.api.JavaVersion

/**
 * The purpose of this object is to hold all of the common Java configuration fields
 * for all Gradle files.
 */
object JavaConfig {

    val JAVA_VERSION = JavaVersion.VERSION_17

    const val JVM_TARGET = "17"
}