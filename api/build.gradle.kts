plugins {
    alias(libs.plugins.jetbrains.kotlin.jvm)
    alias(libs.plugins.serialization)
}

dependencies {

    implementation(libs.json.serialization)
}