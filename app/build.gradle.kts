import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.ksp)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.junit5)
}

android {
    namespace = "com.example.newspulse"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.newspulse"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }

        javaCompileOptions {
            annotationProcessorOptions {
                arguments["room.schemaLocation"] = "$projectDir/schemas"
            }
        }
    }

    android.buildFeatures.buildConfig = true
    buildTypes {
        val apiKey = gradleLocalProperties(projectRootDir = rootDir, providers = rootProject.providers).getProperty("API_KEY")
        debug {
            buildConfigField("String", "API_KEY", "\"$apiKey\"")
            buildConfigField("String", "BASE_URL", "\"https://newsapi.org\"")
        }
        release {
            buildConfigField("String", "API_KEY", "\"$apiKey\"")
            buildConfigField("String", "BASE_URL", "\"https://newsapi.org\"")
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        isCoreLibraryDesugaringEnabled = true
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.9"
    }
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_11.toString()
    }
    buildFeatures {
        compose = true
        buildConfig = true
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

kotlin {
    compilerOptions {
        freeCompilerArgs.add("-opt-in=kotlin.RequiresOptIn")
    }
}

dependencies {

    // Android + compose
    implementation(libs.bundles.compose)

    // splash
    implementation(libs.androidx.core.splashscreen)

    // Icon
    implementation(libs.androidx.material.icons.extended)

    // paging
    implementation(libs.bundles.pagin)

    // Coin
    implementation(libs.coil.compose)

    // Navigation
    implementation(libs.androidx.navigation.compose)

    // Destination
    implementation(libs.compose.destinations)
    testImplementation(libs.junit.jupiter)
    testImplementation(libs.junit.jupiter)
    testImplementation(libs.junit.jupiter)
    testImplementation(libs.junit.jupiter)
    testImplementation(libs.jupiter.junit.jupiter)
    testImplementation(libs.jupiter.junit.jupiter)
    testImplementation(libs.jupiter.junit.jupiter)
    ksp(libs.compose.destinations.ksp)

    // LifeCycle
    implementation(libs.bundles.lifecycle)

    // Coroutine
    implementation(libs.bundles.coroutines)

    // Koin
    implementation(libs.bundles.koin)

    // Ktor
    implementation(libs.bundles.ktor)

    // Room
    implementation(libs.room.runtime)
    implementation(libs.room.ktx)
    ksp(libs.room.compiler)

    // Timber
    implementation(libs.timber)

    // Serialization
    implementation(libs.kotlinx.serialization.json)

    // Pagination
    implementation(libs.bundles.pagin)

    // BOM
    implementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(platform(libs.androidx.compose.bom))

    // Test
    testImplementation(kotlin("test"))
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    debugImplementation(libs.bundles.compose.debug)

    // Desugaring
    coreLibraryDesugaring(libs.desugar.jdk.libs)

    // Testing
    testImplementation(libs.bundles.junit)
    testImplementation(libs.bundles.mockk)
    testImplementation(libs.assertk)
    testImplementation(libs.coroutines.test)
    testImplementation(libs.slf4j.simple)
}
