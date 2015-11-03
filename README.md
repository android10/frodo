

Frodo for Android
=========================

[![Hex.pm](https://img.shields.io/hexpm/l/plug.svg)](http://www.apache.org/licenses/LICENSE-2.0) [![Platform](https://img.shields.io/badge/platform-android-green.svg)](http://developer.android.com/index.html)
[![Download](https://api.bintray.com/packages/android10/maven/frodo-plugin/images/download.svg) ](https://bintray.com/android10/maven/frodo-plugin/_latestVersion)

```Frodo``` is an android library inspired by Jake Wharton's [Hugo](https://github.com/JakeWharton/hugo), mainly used for logging [RxJava](https://github.com/ReactiveX/RxJava) [Observables](http://reactivex.io/documentation/observable.html) and [Subscribers](http://reactivex.io/RxJava/javadoc/rx/Subscriber.html) outputs on the logcat.
It generates and weaves code based on annotations only on ```debug``` versions of the application where the plugin is applied, for instance, it is safe to persist any ```Frodo``` annotation in the codebase.

<br>
Main Features
-----------------

- **@RxLogObservable:** Annotated methods which return ```rx.Observables``` will print the following information on android logcat when emitting items:

image

```java
    @RxLogObservable
    public Observable<List<MyDummyClass>> list() {
        return Observable.just(buildDummyList());
    }
```

<br>
- **@RxLogSubscriber:** Annotated classes which are of type ```rx.Subscriber``` will print the following information on android logcat when receiving items from an ```rx.Observable```:

image

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

<br>
Enabling Frodo
-----------------
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
apply plugin: 'com.fernandocejas.frodo
```


<br>
Under the hoods
-----------------

You can check these articles:
articles
And this presentation:
presentation

<br>
Known issues
-----------------

Known issues

<br>
Local Development
-----------------

Here are some useful Gradle commands for executing and installing the library:

 * `./install_frodo.sh` - One time execution command for installing frodo library and dependencies.
 * `./gradlew installFrodoApi` - Installs Frodo Api dependencies.
 * `./gradlew installFrodoRuntime` - Installs Frodo Runtime dependencies.
 * `./gradlew installFrodoPlugin` - Installs Frodo gradle plugin.
 * `./gradlew installFrodoAndroidSample` - Build and installs everything plus android application sample.
 * `./gradlew runUnitTests` - Run unit tests.

<br>
Code style
-----------------

Here you can download and install the java codestyle.
https://github.com/android10/java-code-styles

<br>
License
-----------------

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


![http://www.fernandocejas.com](http://www.android10.org/myimages/android10_logo_big_github.png)
 
