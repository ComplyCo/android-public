import com.complyco.gradle.config.AndroidConfig
import com.complyco.gradle.config.JavaConfig

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
}

android {
    namespace = "com.complyco.sample.xml"
    compileSdk = AndroidConfig.SDK_COMPILE

    defaultConfig {
        applicationId = "com.complyco.sample.xml"
        minSdk = AndroidConfig.SDK_MIN
        targetSdk = AndroidConfig.SDK_TARGET
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
        sourceCompatibility = JavaConfig.JAVA_VERSION
        targetCompatibility = JavaConfig.JAVA_VERSION
    }
    kotlinOptions {
        jvmTarget = JavaConfig.JVM_TARGET
    }
}

dependencies {
    implementation(libs.complyco.recorder.xml)

    implementation(libs.androidx.core.ktx)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}