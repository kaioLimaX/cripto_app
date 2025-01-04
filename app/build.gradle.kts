import java.io.FileInputStream
import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    id("kotlin-kapt")
}


val apikeyPropertiesFile = rootProject.file("apikey.properties")
val apikeyProperties = Properties()
if (apikeyPropertiesFile.exists()) {
    apikeyProperties.load(FileInputStream(apikeyPropertiesFile))
}

android {
    namespace = "com.skymob.cryptoappexemple"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.skymob.cryptoappexemple"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        debug {
            buildConfigField("String", "API_KEY", "\"${apikeyProperties["API_KEY"]}\"")
        }
        release {
            buildConfigField("String", "API_KEY", "\"${apikeyProperties["API_KEY"]}\"")
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    buildFeatures{
        buildConfig = true
        viewBinding = true
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)

    val room_version = "2.6.1"

    implementation("androidx.room:room-runtime:$room_version")
    implementation ("androidx.room:room-ktx:$room_version")
    kapt("androidx.room:room-compiler:$room_version")

    //retrofit
    implementation ("com.squareup.retrofit2:retrofit:2.11.0")
    implementation ("com.squareup.retrofit2:converter-gson:2.11.0")

    //koin
    implementation ("io.insert-koin:koin-android:3.4.0")         // Koin para Android
    implementation ("io.insert-koin:koin-androidx-navigation:3.4.0") // Inclui suporte  Navigation

    //interceptor
    implementation ("com.squareup.okhttp3:logging-interceptor:4.12.0")

    //glide
    implementation ("com.github.bumptech.glide:glide:4.12.0")

    //activity and fragment Ktx for viewModels(
    implementation ("androidx.activity:activity-ktx:1.9.3")
    implementation ("androidx.fragment:fragment-ktx:1.8.5")

    //navigation
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)


    //tests
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}