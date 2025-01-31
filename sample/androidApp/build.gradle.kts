import java.io.ByteArrayOutputStream

plugins {
    kotlin("android")
    id("com.android.application")
}

android {
    namespace = "com.mxalbert.zoomable.sample"
    compileSdk = libs.versions.sdk.compile.get().toInt()
    buildToolsVersion = libs.versions.buildTools.get()

    buildFeatures {
        buildConfig = false
        compose = true
    }

    defaultConfig {
        applicationId = "com.mxalbert.zoomable.sample"
        minSdk = libs.versions.sdk.min.get().toInt()
        targetSdk = libs.versions.sdk.target.get().toInt()
        resourceConfigurations += "en"
        val libVersion = property("VERSION_NAME") as String
        val isSnapshot = libVersion.endsWith("SNAPSHOT")
        val appVersion = if (isSnapshot) {
            ByteArrayOutputStream().use { os ->
//                exec {
//                    setCommandLine("git", "describe", "--tags")
//                    standardOutput = os
//                }
//                os.toString()
                "1-0"
            }
        } else libVersion
        val (version, commitCount) = if (isSnapshot) {
            val description = appVersion.split('-')
            description[0] to description[1].toInt()
        } else {
            libVersion to 0
        }
        versionName = appVersion
        versionCode = commitCount + version.split('.')
            .fold(0) { result, number -> (result + number.toInt()) * 100 }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_11.toString()
    }

    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.compose.compiler.get()
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    packaging {
        resources.excludes += listOf(
            "META-INF/*",
            "**/*.kotlin_builtins",
            "DebugProbesKt.bin"
        )
    }
}

dependencies {
    implementation(project(":sample:shared"))
    implementation(libs.androidx.activity.compose)
}
