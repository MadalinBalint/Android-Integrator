plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
}

apply(from = "../coverage/coverageReport.gradle")

android {
    namespace = "com.mendelin.androidintegrator.shared"
    compileSdk = libs.versions.compileSdkVersion.get().toInt()

    defaultConfig {
        minSdk = libs.versions.minSdkVersion.get().toInt()
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
}

dependencies {
    implementation(libs.apollo.runtime)
    implementation(libs.ktor.client.core)
    implementation(libs.ktor.serialization.kotlinx.xml)

    implementation(libs.timber)

    implementation(platform(libs.koin.bom))
    implementation(libs.koin.android)
    testImplementation(libs.junit)

    debugImplementation(libs.chucker)
    releaseImplementation(libs.chucker.no.op)
}
