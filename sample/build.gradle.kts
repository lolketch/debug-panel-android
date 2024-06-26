plugins {
    id(Plugins.Android.applicationPlugin)
    kotlin(Plugins.Kotlin.androidPlugin)
}

android {
    compileSdk = Project.COMPILE_SDK
    namespace = "com.redmadrobot.debugpanel"

    defaultConfig {
        minSdk = Project.MIN_SDK
        targetSdk = Project.TARGET_SDK
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }

    buildFeatures {
        viewBinding = true
    }
}

dependencies {
    implementation(kotlin("stdlib"))
    implementation(androidx.appcompat)
    implementation(stack.material)
    implementation(androidx.constraintlayout)
    implementation(rmr.flipper)
    implementation(stack.timber)
    implementation(stack.kotlinx.coroutines.android)
    implementation(androidx.lifecycle.runtime)

    // Debug panel dependencies
    debugImplementation(project(":debug-panel-core"))
    debugImplementation(project(":plugins:servers-plugin"))
    debugImplementation(project(":plugins:accounts-plugin"))
    debugImplementation(project(":plugins:app-settings-plugin"))
    debugImplementation(project(":plugins:flipper-plugin"))
    debugImplementation(project(":plugins:variable-plugin"))
//    debugImplementation("com.redmadrobot.debug:panel-core:${project.version}")
//    debugImplementation("com.redmadrobot.debug:accounts-plugin:${project.version}")
//    debugImplementation("com.redmadrobot.debug:servers-plugin:${project.version}")
//    debugImplementation("com.redmadrobot.debug:app-settings-plugin:${project.version}")
//    debugImplementation("com.redmadrobot.debug:flipper-plugin:${project.version}")
//    debugImplementation("com.redmadrobot.debug:variable-plugin:${project.version}")

    //No-op dependency
    releaseImplementation(project(":debug-panel-no-op"))
//    releaseImplementation("com.redmadrobot.debug:panel-no-op:${project.version}")

    implementation(stack.retrofit)
}
