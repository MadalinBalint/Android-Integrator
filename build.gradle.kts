// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.compose.compiler) apply false

    // Make sure that you have the Google services Gradle plugin 4.4.1+ dependency
    id("com.google.gms.google-services") version "4.4.2" apply false

    // Add the dependency for the Crashlytics Gradle plugin
    id("com.google.firebase.crashlytics") version "3.0.3" apply false

    kotlin("jvm") version "2.1.0"
    kotlin("plugin.serialization") version "2.1.0"

    alias(libs.plugins.apollo)
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.kover) apply false
}

apply(from = "coverage/completeCoverageReport.gradle")
