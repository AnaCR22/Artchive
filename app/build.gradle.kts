import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.googleServices)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.ksp)
    alias(libs.plugins.safeargs)
    id("kotlin-parcelize")
}

android {
    namespace = "com.uo300568.artchive"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.uo300568.artchive"
        minSdk = 29
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        val properties = Properties()
        properties.load(rootProject.file("local.properties").inputStream())
        val mapsApiKey = properties.getProperty("MAPS_API_KEY") ?: ""

        buildConfigField("String", "MAPS_API_KEY", "\"$mapsApiKey\"")
        manifestPlaceholders["MAPS_API_KEY"] = mapsApiKey
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    kotlinOptions {
        jvmTarget = "11"
    }

    buildFeatures {
        buildConfig = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.room.common.jvm)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.auth)
    implementation(libs.firebase.firestore)
    implementation(libs.androidx.lifecycle.viewmodel)
    implementation(libs.androidx.lifecycle.runtime)

    // Retrofit
    implementation(libs.retrofit)
    implementation(libs.okhttp)
    implementation(libs.okhttp.logging)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.retrofit.kotlinx.serialization)

    // Glide
    implementation(libs.glide)

    implementation(libs.navigation.fragment.ktx)
    implementation(libs.navigation.ui.ktx)
    implementation(libs.androidx.splashscreen)

    implementation(libs.play.services.maps)
    implementation(libs.play.services.location)
    implementation(libs.photoview)

}