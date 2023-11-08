plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "com.ghn.sdk"
    compileSdk = 33

    defaultConfig {
        applicationId = "com.ghn.sdk"
        minSdk = 24
        targetSdk = 33
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
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures{
        viewBinding=true
        dataBinding=true
    }
}

dependencies {

    implementation("androidx.core:core-ktx:1.9.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.8.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation(project(mapOf("path" to ":mylibrary")))
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
//    implementation ("com.trello.rxlifecycle4:rxlifecycle:4.0.2")
    // If you want to bind to Android-specific lifecycles
    implementation ("com.trello.rxlifecycle4:rxlifecycle-android:4.0.2")
    // If you want pre-written Activities and Fragments you can subclass as providers
    implementation  ("com.trello.rxlifecycle4:rxlifecycle-components:4.0.2")
    // If you want pre-written support preference Fragments you can subclass as providers
//    implementation  ("com.trello.rxlifecycle4:rxlifecycle-components-preference:4.0.2")
    // If you want to use Android Lifecycle for providers
//    implementation  ("com.trello.rxlifecycle4:rxlifecycle-android-lifecycle:4.0.2")
    // If you want to use Kotlin syntax
//    implementation  ("com.trello.rxlifecycle4:rxlifecycle-kotlin:4.0.2")
    // If you want to use Kotlin syntax with Android Lifecycle
//    implementation  ("com.trello.rxlifecycle4:rxlifecycle-android-lifecycle-kotlin:4.0.2")
}