plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "gmikhail.colorpicker"
    compileSdk = 37

    defaultConfig {
        applicationId = "gmikhail.colorpicker"
        minSdk = 32
        targetSdk = 37
        versionCode = 109
        versionName = "11.1.1"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
        }
    }
}
