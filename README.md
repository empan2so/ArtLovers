# ArtLovers 🎨

Android application that displays lists of artworks from the [Art Institute of Chicago REST API](https://api.artic.edu/docs/) via Retrofit and saves "loved" artwork data to a local database using Room.

Swipe between two feeds - Home (default and searchable) and Loved and click on feed items to view more detail on the detail screen including an image of the piece.

![](screenshots.png)

## Architecture
- Single-activity architecture using ViewPager2 and NavController to navigate between Fragments.
- MVVM pattern with Fragments that observe LiveData from ViewModels.
- Repository provides access to local and remote data sources.
- The LocalDataSource emits LiveData directly from the Dao while the RemoteDataSource emits a Flow of the API response, both on I/O threads.
- Dependency injection using Dagger-Hilt

Data flow chart:
![](dataflow.jpg)

## Libraries used:
- [Dagger-Hilt](https://dagger.dev/hilt/) - dependency injection
- [Retrofit](https://square.github.io/retrofit/) - REST client for Android
- [Gson](https://github.com/google/gson) - serialization/deserialization library to convert JSON
- [Room](https://developer.android.com/jetpack/androidx/releases/room) - persistence library provides an abstraction layer over SQLite
- [Coroutines](https://kotlinlang.org/docs/coroutines-overview.html) - asynchronously run I/O tasks
- [Flow](https://kotlinlang.org/api/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.flow/-flow/) - asynchronous data stream to emit network responses
- [LiveData](https://developer.android.com/reference/androidx/lifecycle/LiveData) - observable data holder used in ViewModels
- [ViewModel](https://developer.android.com/reference/androidx/lifecycle/ViewModel) - prepares and manages data for Fragments
- [ViewPager2](https://developer.android.com/reference/androidx/viewpager2/widget/ViewPager2) - navigating between home and loved feed fragments horizontally
- [NavController](https://developer.android.com/reference/androidx/navigation/NavController) - navigating between feed and detail fragments with navigation graph
- [Picasso](https://square.github.io/picasso/) - image downloading/cacheing library