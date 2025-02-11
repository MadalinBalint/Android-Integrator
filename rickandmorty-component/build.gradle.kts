plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.apollo)
}

apply(from = "../coverage/coverageReport.gradle")

android {
    namespace = "com.mendelin.androidintegrator.rickandmorty.component"
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
    implementation(project(path = ":shared"))
    testImplementation(libs.junit)

    // Apollo GraphQL
    implementation(libs.apollo.runtime)

    implementation(platform(libs.koin.bom))
    implementation(libs.koin.android)
}

apollo {
    service("rickAndMorty") {
        packageName.set("com.mendelin.androidintegrator")
        schemaFile.set(file("src/main/graphql/schema.graphqls"))
    }
}
