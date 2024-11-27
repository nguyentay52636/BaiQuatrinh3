plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.example.baitapquatrinh3"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.baitapquatrinh3"
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
}

dependencies {
    implementation("androidx.appcompat:appcompat:1.3.1")
    implementation ("androidx.core:core:1.7.0")
       implementation ("com.github.bumptech.glide:glide:4.15.1")
    implementation ("com.github.chrisbanes:PhotoView:2.3.0")
    annotationProcessor  ("com.github.bumptech.glide:compiler:4.15.1")
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
}