plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "com.androidify.myandroida"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.androidify.myandroida"
        minSdk = 23
        targetSdk = 35
        versionCode = 6
        versionName = "1.0.6"
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
}
