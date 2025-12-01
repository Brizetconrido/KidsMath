plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
}


android {
    namespace = "com.example.kidsmath"
    compileSdk = 34

    /** Configurar Java Toolchain a 17 (evita todos los warnings) */
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = "17"
    }

    defaultConfig {
        applicationId = "com.example.kidsmath"
        minSdk = 21
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

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

    /** Material Design */
    implementation("com.google.android.material:material:1.10.0")

    /** API Client (Volley) */
    implementation("com.android.volley:volley:1.2.1")

    /** OkHttp */
    implementation("com.squareup.okhttp3:okhttp:4.11.0")

    /** CameraX */
    implementation("androidx.camera:camera-core:1.3.2")
    implementation("androidx.camera:camera-camera2:1.3.2")
    implementation("androidx.camera:camera-lifecycle:1.3.2")
    implementation("androidx.camera:camera-view:1.3.2")

    /** ML Kit */
    implementation("com.google.mlkit:object-detection:16.1.0")

    /** Lifecycle */
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.7.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.7.0")
}
