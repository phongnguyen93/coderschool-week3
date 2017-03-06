# Project 3 - *NoisyBirdy*

**NoisyBirdy** is an android app that allows a user to view his Twitter timeline and post a new tweet. The app utilizes [Twitter REST API](https://dev.twitter.com/rest/public).

Time spent: **24** hours spent in total

## User Stories

The following **required** functionality is completed:

* [x]	User can **sign in to Twitter** using OAuth login
* [x]	User can **view tweets from their home timeline**
  * [x] User is displayed the username, name, and body for each tweet
  * [x] User is displayed the [relative timestamp](https://gist.github.com/nesquena/f786232f5ef72f6e10a7) for each tweet "8m", "7h"
  * [ ] User can view more tweets as they scroll with [infinite pagination](http://guides.codepath.com/android/Endless-Scrolling-with-AdapterViews-and-RecyclerView). Number of tweets is unlimited.
    However there are [Twitter Api Rate Limits](https://dev.twitter.com/rest/public/rate-limiting) in place.
* [x] User can **compose and post a new tweet**
  * [x] User can click a “Compose” icon in the Action Bar on the top right
  * [x] User can then enter a new tweet and post this to twitter
  * [x] User is taken back to home timeline with **new tweet visible** in timeline

The following **optional** features are implemented:

* [x] User can **see a counter with total number of characters left for tweet** on compose tweet page
* [ ] User can **click a link within a tweet body** on tweet details view. The click will launch the web browser with relevant page opened.
* [x] User can **pull down to refresh tweets timeline**
* [x] User can **open the twitter app offline and see last loaded tweets**. Persisted in SQLite tweets are refreshed on every application launch. While "live data" is displayed when app can get it from Twitter API, it is also saved for use in offline mode.
* [ ] User can tap a tweet to **open a detailed tweet view**
* [ ] User can **select "reply" from detail view to respond to a tweet**
* [x] Improve the user interface and theme the app to feel "twitter branded"

The following **bonus** features are implemented:

* [ ] User can see embedded image media within the tweet detail view
* [ ] User can watch embedded video within the tweet
* [x] Compose tweet functionality is build using modal overlay
* [ ] Use Parcelable instead of Serializable using the popular [Parceler library](http://guides.codepath.com/android/Using-Parceler).
* [x] [Leverage RecyclerView](http://guides.codepath.com/android/Using-the-RecyclerView) as a replacement for the ListView and ArrayAdapter for all lists of tweets.
* [x] Move the "Compose" action to a [FloatingActionButton](https://github.com/codepath/android_guides/wiki/Floating-Action-Buttons) instead of on the AppBar.
* [x] On the Twitter timeline, leverage the [CoordinatorLayout](http://guides.codepath.com/android/Handling-Scrolls-with-CoordinatorLayout#responding-to-scroll-events) to apply scrolling behavior that [hides / shows the toolbar](http://guides.codepath.com/android/Using-the-App-ToolBar#reacting-to-scroll).
* [ ] Replace all icon drawables and other static image assets with [vector drawables](http://guides.codepath.com/android/Drawables#vector-drawables) where appropriate.
* [ ] Leverages the [data binding support module](http://guides.codepath.com/android/Applying-Data-Binding-for-Views) to bind data into layout templates.
* [x] Replace Picasso with [Glide](http://inthecheesefactory.com/blog/get-to-know-glide-recommended-by-google/en) for more efficient image rendering.
* [ ] Enable your app to [receive implicit intents](http://guides.codepath.com/android/Using-Intents-to-Create-Flows#receiving-implicit-intents) from other apps.  When a link is shared from a web browser, it should pre-fill the text and title of the web page when composing a tweet.
* [ ] When a user leaves the compose view without publishing and there is existing text, prompt to save or delete the draft.  The draft can be resumed from the compose view.

The following **additional** features are implemented:

* Apply some basic Clean Architecture (https://fernandocejas.com/2015/07/18/architecting-android-the-evolution/) 
  with Reactive Programming (RxJava2 & RxAndroid) + Dagger + Butterknife
  - App structure is divide into 3 main layer:
    + *Data*: In this layer, I use Factory Pattern to produce ApiDataStore or LocalDataStore implementation base on some logic condition, 
      then data will be wrapped in an Observable which will emit our data to upper layer, in this layer data which is retrieved will be push to upper layer
      throught an interface to decouple between each layer.
    + *Domain*: Here is layer which contain all the app logic. In this layer, I implemented app's usecase (get home timeline, post tweet) and transform data retrieved
      from lower layer (Data) to POJO object. Also, I created a Subscriber which observered the Observable data got from Data layer to handle success, error case
    + *Presenter*: Presenter is where i used MVP pattern to use data which retrieved and modified in lower layer, View (Activity,Fragment) will be handle display action only
      all the implementation lay in Presenter which will handle Model objects (data retrieved from Domain). Here is also the layer i used Dagger2 to localize all the needed method
      injected and provided them 

## Video Walkthrough

Here's a walkthrough of implemented user stories:

<img src='http://i.imgur.com/ebYoFGR.gif' title='Video Walkthrough' width='380' height='700' alt='Video Walkthrough' />

GIF created with [LiceCap](http://www.cockos.com/licecap/).

## Notes

Well, it take a lot of time to learn and put what i haved learn into this projects. All the patterns, structures at first time
that was really a rough stuffs but when i used them and know how they work, which benefit can get from them, it's like they enlighten me.
By the way, in this assignment i "sacraficed" the UI/UX for the App structure design, so maybe it's look a little bit simple 
but like they said "Handsome is as handsome does"

**IMPORTANT: there is an issue when open app and then navigate to browser to make request authen, but after authorize you must close the browser and then use app or the app keep open browser to request auth. I'm doing my best to fix that. 
!!! SORRY FOR THAT INCONVENIENCE !!!**


## License

    Copyright [yyyy] [name of copyright owner]

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

        http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
