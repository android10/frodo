

Frodo 
=========================

[![Hex.pm](https://img.shields.io/hexpm/l/plug.svg)](http://www.apache.org/licenses/LICENSE-2.0) [![Platform](https://img.shields.io/badge/platform-android-green.svg)](http://developer.android.com/index.html)
[![Download](https://api.bintray.com/packages/android10/maven/frodo-plugin/images/download.svg) ](https://bintray.com/android10/maven/frodo-plugin/_latestVersion)

```Frodo``` is an android library inspired by Jake Wharton's [Hugo](https://github.com/JakeWharton/hugo), mainly used for logging [RxJava](https://github.com/ReactiveX/RxJava) [Observables](http://reactivex.io/documentation/observable.html) and [Subscribers](http://reactivex.io/RxJava/javadoc/rx/Subscriber.html) outputs on the logcat.
It generates and weaves code based on annotations only on ```debug``` versions of the application where the plugin is applied, for instance, it is safe to persist any ```Frodo``` annotation in the codebase.

![frodo_hug](https://cloud.githubusercontent.com/assets/1360604/10925718/e7ea4318-8290-11e5-91b4-f2bfbde65319.gif)

Main Features
=========================

- **@RxLogObservable:** Annotated methods which return ```rx.Observables``` will print the following information when emitting items:

<img width="767" alt="frodo_observable" src="https://cloud.githubusercontent.com/assets/1360604/10925000/ee937c08-828a-11e5-97ac-bb13b7d469f8.png">

```java
    @RxLogObservable
    public Observable<List<MyDummyClass>> list() {
        return Observable.just(buildDummyList());
    }
```

- **@RxLogObservable.Scope Options:** It is possible to narrow down the debug information shown by adding a debugging scope to @RxLogObservable annotation. 

     -  **Scope.EVERYTHING:** Logs stream data, schedulers and rx.Observable events. Default.
     -  **Scope.STREAM:** Logs rx.Observable emitted items plus total execution time.
     -  **Scope.SCHEDULERS:** Logs schedulers where the annotated rx.Observable operates on.
     -  **Scope.EVENTS:** Logs rx.Observable events only.
     -  **Scope.NOTHING:** Turns off logging for the annotated rx.Observable.

```java
    @RxLogObservable(Scope.STREAM)
    public Observable<List<MyDummyClass>> list() {
        return Observable.just(buildDummyList());
    }
```

- **@RxLogSubscriber:** Annotated classes which are of type ```rx.Subscriber``` will print the following information when receiving items from an ```rx.Observable```:

<img width="980" alt="frodo_subscriber" src="https://cloud.githubusercontent.com/assets/1360604/10925010/fa76523e-828a-11e5-8607-1611aef61add.png">

```java
@RxLogSubscriber
public class MySubscriberBackpressure extends Subscriber<Integer> {

    @Override
    public void onStart() {
        request(40);
    }

    @Override
    public void onNext(Integer value) {
        //empty
    }

    @Override
    public void onError(Throwable throwable) {
        //empty
    }

    @Override
    public void onCompleted() {
        if (!isUnsubscribed()) {
            unsubscribe();
        }
    }
}
```

Enabling Frodo
=========================
To enable Frodo, a gradle plugin must be applied in your ```build.gradle```:

```java
buildscript {
  repositories {
    jcenter()
  }
  dependencies {
    classpath "com.fernandocejas.frodo:frodo-plugin:${latest_version}"
  }
}

apply plugin: 'com.android.application'
apply plugin: 'com.fernandocejas.frodo'

//By default frodo is ON on debug build variants, although
//we can enable-disable it with this configuration.
frodo {
  enabled = true
}
```

Known issues
=========================

1 - Multi module setup (application + android library) will not log annotated methods/classes from Android Library Module but will do it on Android Application Module. The reason behind this, is that the Android Gradle Plugin will build all Android Libraries as release versions, for instance, Frodo is not able to weave any code on the annotated methods/classes (Remember that only weaves in debug versions). There is a workaround for forcing debug versions of your Android Libraries (just be careful in case this is forgotten and you end up shipping a version of your app with RxJava Logging enabled) by adding this line in your ```build.gradle``` file:

```java
android {
  defaultPublishConfig "debug"
}
```

Frodo WIKI
=========================
For complete information, features and usage, refer to the [WIKI](https://github.com/android10/frodo/wiki):
- [@RxLogObservable](https://github.com/android10/frodo/wiki/@RxLogObservable)
- [@RxLogSubscriber](https://github.com/android10/frodo/wiki/@RxLogSubscriber)
- [Release Notes](https://github.com/android10/frodo/wiki/Release-Notes)
- [Development](https://github.com/android10/frodo/wiki/Development)
- [Frodo under the hoods](https://github.com/android10/frodo/wiki/Under-the-hoods)

License
=========================

    Copyright 2015 Fernando Cejas

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.


![http://www.fernandocejas.com](https://github.com/android10/Sample-Data/blob/master/android10/android10_logo_big.png)

<a href="https://www.buymeacoffee.com/android10" target="_blank"><img src="https://www.buymeacoffee.com/assets/img/custom_images/orange_img.png" alt="Buy Me A Coffee" style="height: auto !important;width: auto !important;" ></a>
