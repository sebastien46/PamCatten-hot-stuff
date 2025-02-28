// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("com.android.application") version "8.3.1" apply false
    id("org.jetbrains.kotlin.android") version "1.9.0" apply false
    id("org.jetbrains.dokka") version "1.9.10" apply false
    id("com.google.dagger.hilt.android") version "2.44" apply false
    id("com.google.android.gms.oss-licenses-plugin") version("0.10.6") apply(false)
}