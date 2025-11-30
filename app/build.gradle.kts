plugins {
    alias(libs.plugins.android.application) apply true
}

android {
    namespace = "com.example.kidsmath"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.kidsmath"
        minSdk = 21
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        // Requerido si usas SpeechRecognizer + Android 12+
        manifestPlaceholders["appExported"] = "true"
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
}

dependencies {

    implementation("com.google.android.material:material:1.10.0")

    implementation("com.squareup.okhttp3:okhttp:4.11.0")

    implementation("androidx.camera:camera-core:1.3.2")
    implementation("androidx.camera:camera-camera2:1.3.2")
    implementation("androidx.camera:camera-lifecycle:1.3.2")
    implementation("androidx.camera:camera-view:1.3.2")

    implementation("com.google.mlkit:object-detection:16.1.0")

    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.7.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.7.0")

    implementation("com.android.volley:volley:1.2.1")
}
