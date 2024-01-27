// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    repositories {
        google()
        mavenCentral()
    }

    dependencies {
        classpath(Dependencies.hiltAgp)
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.9.0")
    }
}

val properties = java.util.Properties().apply {
    load(rootProject.file("local.properties").reader())
}