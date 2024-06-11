plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.serialization)
    id(libs.plugins.kapt.get().pluginId)
    alias(libs.plugins.hilt)
}

android {
    namespace = "com.andr.zahar2.gesturetestapp"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.andr.zahar2.gesturetestapp"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {

    implementation(project(":api"))

    implementation(libs.androidx.core.ktx)

    implementation(libs.client.core)
    implementation(libs.client.okhttp)
    implementation(libs.client.websockets)
    implementation(libs.json.serialization)
    implementation(libs.ktor.serialization)

    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}

kapt {
    correctErrorTypes = true
}