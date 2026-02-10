plugins {
    alias(libs.plugins.android.application)
    id("com.google.gms.google-services")
}

android {
    namespace = "com.example.tasty_food_app"
    compileSdk {
        version = release(36)
    }

    defaultConfig {
        applicationId = "com.example.tasty_food_app"
        minSdk = 24
        targetSdk = 36
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

dependencies {
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    implementation(libs.navigation.fragment.ktx)
    implementation(libs.navigation.ui.ktx)
    implementation(libs.fragment)
    implementation(libs.viewpager2)
    implementation(libs.recyclerview)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)

    implementation(libs.lottie)
    implementation(libs.splashscreen)


    implementation(platform("com.google.firebase:firebase-bom:34.8.0"))

    implementation("com.google.firebase:firebase-auth")
    implementation ("com.google.firebase:firebase-firestore")


    implementation("com.google.android.gms:play-services-auth:21.0.0")
    implementation("androidx.credentials:credentials:1.3.0")
    implementation("androidx.credentials:credentials-play-services-auth:1.3.0")
    implementation("com.google.android.libraries.identity.googleid:googleid:1.1.1")

    implementation(libs.retrofit)
    implementation(libs.convertar.gson)
    implementation(libs.glide)
    val room_version = "2.8.4"

    implementation(libs.room.runtime)
    annotationProcessor(libs.room.compiler)

    //Rx android
    implementation("io.reactivex.rxjava3:rxandroid:3.0.2")
    implementation("io.reactivex.rxjava3:rxjava:3.1.6")

    implementation("com.github.akarnokd:rxjava3-retrofit-adapter:3.0.0")

    implementation("androidx.room:room-rxjava3:$room_version")


    implementation("com.pierfrancescosoffritti.androidyoutubeplayer:core:13.0.0")

}