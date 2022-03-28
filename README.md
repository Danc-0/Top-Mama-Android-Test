Top Mama Android Assignment
==============

Writing Weather App using Kotlin.

Requirements
----  
- A screen displaying a list of current temperatures from 20 cities
- A search bar for filtering these cities
- Items from the list can be able to be made a favorite, favourite cities should go to the top of the list
- Clicking on the items should open an item screen displaying more detailed info of weather in that city
- A notification showing the current conditions of a favorite city at the top of the hour (when the app is not in the foreground)
  
The API I am using is `http://api.weatherapi.com/v1/current.json?key=&q=London&aqi=no`

### How it's built

* Technologies used
    * [Kotlin](https://kotlinlang.org/)
    * [Coroutines](https://kotlinlang.org/docs/reference/coroutines-overview.html)
    * [Flow](https://kotlinlang.org/docs/reference/coroutines/flow.html)
    * [Retrofit](https://square.github.io/retrofit/)
    * [Jetpack](https://developer.android.com/jetpack)
        * [Lifecycle](https://developer.android.com/topic/libraries/architecture/lifecycle)
        * [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel)

* Architecture
    * Clean Architecture 
    * MVVM - Model View View Model

### Screenshots

Added some screenshots in the `screenshots` folder, in the root directory of the project.

Main Screen | Search View | Detail Screen
--- | --- | ---
<img src="https://github.com/Danc-0/Top-Mama-Android-Test/blob/main/screenshots/main_screen.jpg" width="280"/> | <img src="https://github.com/Danc-0/Top-Mama-Android-Test/blob/main/screenshots/search_view.jpg" width="280"/> | <img src="https://github.com/Danc-0/Top-Mama-Android-Test/blob/main/screenshots/details_screen.jpg" width="280"/>
