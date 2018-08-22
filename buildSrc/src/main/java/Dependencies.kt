object Versions {

    const val GRADLE = "3.1.4"
    const val KOTLIN = "1.2.41"
    const val OBJECT_BOX = "1.5.0"
    const val ANDROID_SUPPORT = "27.1.1"
    const val CONSTRAINT_LAYOUT = "2.0.0-alpha1"
    const val DAGGER = "2.16"
    const val GLIDE = "4.7.1"
    const val RX_JAVA = "2.1.14"
    const val RX_ANDROID = "2.0.2"
    const val RX_BINDING = "2.1.1"
    const val TIMBER = "4.7.0"
    const val J_UNIT = "4.12"
    const val TEST_RUNNER = "1.0.2"
    const val ESPRESSO = "3.0.2"
}

object Libraries {

    const val KOTLIN_JDK = "org.jetbrains.kotlin:kotlin-stdlib-jdk7:${Versions.KOTLIN}"
    const val OBJECT_BOX_KOTLIN = "io.objectbox:objectbox-kotlin:${Versions.OBJECT_BOX}"
    const val OBJECT_BOX_ANDROID = "io.objectbox:objectbox-android:${Versions.OBJECT_BOX}"
    const val ANDROID_SUPPORT_APPCOMPAT = "com.android.support:appcompat-v7:${Versions.ANDROID_SUPPORT}"
    const val ANDROID_SUPPORT_DESIGN = "com.android.support:design:${Versions.ANDROID_SUPPORT}"
    const val ANDROID_SUPPORT_PALETTE = "com.android.support:palette-v7:${Versions.ANDROID_SUPPORT}"
    const val CONSTRAINT_LAYOUT = "com.android.support.constraint:constraint-layout:${Versions.CONSTRAINT_LAYOUT}"
    const val DAGGER_COMPILER = "com.google.dagger:dagger-compiler:${Versions.DAGGER}"
    const val DAGGER_ANDROID_PROCESSOR = "com.google.dagger:dagger-android-processor:${Versions.DAGGER}"
    const val DAGGER_ANDROID_SUPPORT = "com.google.dagger:dagger-android-support:${Versions.DAGGER}"
    const val DAGGER = "com.google.dagger:dagger:${Versions.DAGGER}"
    const val GLIDE = "com.github.bumptech.glide:glide:${Versions.GLIDE}"
    const val GLIDE_COMPILER = "com.github.bumptech.glide:compiler:${Versions.GLIDE}"
    const val RX_JAVA = "io.reactivex.rxjava2:rxjava:${Versions.RX_JAVA}"
    const val RX_ANDROID = "io.reactivex.rxjava2:rxandroid:${Versions.RX_ANDROID}"
    const val RX_BINDING = "com.jakewharton.rxbinding2:rxbinding-kotlin:${Versions.RX_BINDING}"
    const val TIMBER = "com.jakewharton.timber:timber:${Versions.TIMBER}"
    const val J_UNIT = "junit:junit:${Versions.J_UNIT}"
    const val TEST_RUNNER = "com.android.support.test:runner:${Versions.TEST_RUNNER}"
    const val ESPRESSO = "com.android.support.test.espresso:espresso-core:${Versions.ESPRESSO}"
}

object Android {

    const val APPLICATION_ID = "com.chrynan.launcher"
    const val COMPILE_SDK_VERSION = 27
    const val MIN_SDK_VERSION = 23
    const val TARGET_SDK_VERSION = 27
    const val VERSION_CODE = 1
    const val VERSION_NAME = "1.0"
    const val TEST_INSTRUMENTATION_RUNNER = "android.support.test.runner.AndroidJUnitRunner"
}

object ClassPath {

    const val KOTLIN_PLUGIN = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.KOTLIN}"
    const val OBJECT_BOX_PLUGIN = "io.objectbox:objectbox-gradle-plugin:${Versions.OBJECT_BOX}"
    const val GRADLE = "com.android.tools.build:gradle:${Versions.GRADLE}"
}