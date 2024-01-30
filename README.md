# BitCupApp
Pexels App is an application that provides access to an extensive collection of high-quality, free stock photos for personal and commercial use

## Architecture
This application follows the classic SOLID based clean architecture approach. This approach differs from
[official architecture guidance](https://developer.android.com/topic/architecture), but it is actively used by many developers.
With this approach, the amount of boilerplate code increases, in favor of standardization and scalability.

## Features
- [Hilt](https://developer.android.com/training/dependency-injection/hilt-android) for dependencies injection
- [Room](https://developer.android.com/training/data-storage/room) for caching
- [Retrofit](https://square.github.io/retrofit/) 
- [OkHttp](https://square.github.io/okhttp/)
- [MVVM](https://ru.wikipedia.org/wiki/Model-View-ViewModel)
- [WorkManager](https://developer.android.com/topic/libraries/architecture/workmanager) for removing data every hour if it contains some data
- [Splash Screen Api](https://developer.android.com/develop/ui/views/launch/splash-screen) to delete data every hour if it exists in the local database
- [buildSrc](https://blogs.halodoc.io/streamline-android-app-dependencies-with-buildsrc/)
- [Flow](https://developer.android.com/kotlin/flow)
- [Coroutines](https://developer.android.com/kotlin/coroutines)
- [Glide](https://github.com/bumptech/glide) for loading and caching images
- [LiveData](https://developer.android.com/topic/libraries/architecture/livedata)

# Fragments

## **HomeFragment**

<p align="center">
<img  src="./images_readme/HomeFragment.jpg" width="30%">
</p>

## **DetailsFragment**

<p align="center">
<img  src="./images_readme/DetailsFragment.jpg" width="30%">
</p>

## **BookmarksFragment**

<p align="center">
<img  src="./images_readme/BookmarksFragment.jpg" width="30%">
</p>
