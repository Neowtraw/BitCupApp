import org.gradle.api.artifacts.dsl.DependencyHandler
import org.gradle.kotlin.dsl.project

/**
 * Dependencies
 */
object Dependencies {

    const val hiltAndroid = "com.google.dagger:hilt-android:${Versions.hilt}"
    const val hiltCompiler = "com.google.dagger:hilt-android-compiler:${Versions.hilt}"
    const val hiltAgp = "com.google.dagger:hilt-android-gradle-plugin:${Versions.hilt}"
    const val hiltCompose = "androidx.hilt:hilt-work:${Versions.hiltCompose}"
    const val hiltCompilerKapt = "androidx.hilt:hilt-compiler:${Versions.hiltCompose}"
    const val hiltNavigation = "androidx.hilt:hilt-navigation-compose:${Versions.hiltNavigation}"

    const val okHttp = "com.squareup.okhttp3:okhttp:${Versions.okHttp}"
    const val okHttpLoggingInterceptor = "com.squareup.okhttp3:logging-interceptor:${Versions.okHttp}"

    const val retrofit = "com.squareup.retrofit2:retrofit:${Versions.retrofit}"
    const val moshiConverter = "com.squareup.retrofit2:converter-moshi:${Versions.retrofit}"
    const val gsonConverter = "com.squareup.retrofit2:converter-gson:${Versions.retrofit}"

    const val roomRuntime = "androidx.room:room-runtime:${Versions.room}"
    const val roomCompiler = "androidx.room:room-compiler:${Versions.room}"
    const val roomKtx = "androidx.room:room-ktx:${Versions.room}"
    const val roomCompilerAnnotation = "androidx.room:room-compiler:${Versions.room}"
    const val roomTest = "androidx.room:room-testing:${Versions.room}"

    const val coroutines = "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.coroutines}"
    const val coroutinesCore = "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.coroutines}"
    const val coroutinesTest = "org.jetbrains.kotlinx:kotlinx-coroutines-test:${Versions.coroutines}"

    const val viewModel = "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.viewModel}"
    const val viewModelLivedata = "androidx.lifecycle:lifecycle-livedata-ktx:${Versions.viewModel}"

    const val glide = "com.github.bumptech.glide:glide:${Versions.glide}"
    const val glideCompiler = "com.github.bumptech.glide:compiler:${Versions.glide}"
    const val glideCompose = "com.github.bumptech.glide:compose:${Versions.glideCompose}"

    const val recyclerView = "androidx.recyclerview:recyclerview:${Versions.recyclerView}"
    const val fragment = "androidx.fragment:fragment-ktx:${Versions.fragments}"
    const val gson = "com.google.code.gson:gson:${Versions.gson}"
}

/**
 *version's connection
 */
fun DependencyHandler.room() {
    implementation(Dependencies.roomRuntime)
    implementation(Dependencies.roomKtx)
    annotationProcessor(Dependencies.roomCompilerAnnotation)
    kapt(Dependencies.roomCompiler)
    testImplementation(Dependencies.roomTest)
}

fun DependencyHandler.retrofit() {
    implementation(Dependencies.retrofit)
    implementation(Dependencies.gsonConverter)
    implementation(Dependencies.okHttp)
    implementation(Dependencies.okHttpLoggingInterceptor)
}

fun DependencyHandler.hilt() {
    implementation(Dependencies.hiltAndroid)
    implementation(Dependencies.hiltCompose)
    kapt(Dependencies.hiltCompiler)
    kapt(Dependencies.hiltCompilerKapt)
    implementation(Dependencies.hiltNavigation)
}

fun DependencyHandler.viewModel() {
    implementation(Dependencies.viewModel)
    implementation(Dependencies.viewModelLivedata)
}

fun DependencyHandler.coroutines() {
    implementation(Dependencies.coroutines)
    implementation(Dependencies.coroutinesCore)
    testImplementation(Dependencies.coroutinesTest)
}

fun DependencyHandler.base() {
    implementation(Dependencies.fragment)
    implementation(Dependencies.recyclerView)
}

fun DependencyHandler.gson(){
    implementation(Dependencies.gson)
}

fun DependencyHandler.glide(){
    implementation(Dependencies.glide)
    implementation(Dependencies.glideCompose)
    annotationProcessor(Dependencies.glideCompiler)
}

/**
 * module's connection
 */
//fun DependencyHandler.domain() {
//    implementation(project(":domain"))
//}
//
//fun DependencyHandler.data() {
//    implementation(project(":data"))
//}