plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
}

android {
    namespace = "com.example.sampleapp"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.sampleapp"
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

    // Defined product favour
    flavorDimensions.add("version")
    productFlavors {

        create("sampleApp1") {
            applicationId = "com.example.sampleapp1"
            versionNameSuffix = "-v1"
        }
        create("sampleApp2") {
            applicationId = "com.example.sampleapp2"
            versionNameSuffix = "-v2"
        }
        create("sampleApp3") {
            applicationId = "com.example.sampleapp3"
            versionNameSuffix = "-v3"
        }
        create("sampleApp4") {
            applicationId = "com.example.sampleapp4"
            versionNameSuffix = "-v4"
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

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}