Gandalf for Android
=========================

Gandalf is an android development toolkit, mainly using for logging outputs on the logcat.
It generates and injects code base on annotations and methods executions only on ```debug``` versions of the application where the plugin is applied, for instance is safe to persist any Gandalf code in the codebase.

<br>
Main Features
-----------------

- **StrictMode:** It is enabled by default if there is #android.app.Application as entry point of you app, otherwise, you can use ```Gandalf.enableStrictMode()``` at any point to enable it.

```java
Gandalf.enableStrictMode()
```

<br>
- **@RxLogObservable:** Annotated methods which return ```rx.Observables``` will print the following information on android logcat when emitting items:

<img width="799" alt="gandalf_observable" src="https://cloud.githubusercontent.com/assets/1360604/9748498/62b5ebd4-5685-11e5-948f-a4306887863c.png">

```java
    @RxLogObservable
    public Observable<List<MyDummyClass>> list() {
        return Observable.just(buildDummyList());
    }
```

<br>
- **@RxLogSubscriber:** Annotated classes which are of type ```rx.Subscriber``` will print the following information on android logcat when receiving items from an ```rx.Observable```:

<img width="1008" alt="gandalf_subscriber" src="https://cloud.githubusercontent.com/assets/1360604/9748589/f857afa6-5685-11e5-92b5-9329b3576123.png"> 

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
- **@DebugLog:** method and constructor annotation that will print:

<img width="903" alt="gandalf_log" src="https://cloud.githubusercontent.com/assets/1360604/9748608/291f0350-5686-11e5-98b2-c5b50b17a433.png">

```java
@DebugLog
public void doSomethingElse(int someValue, String someString) {
    sleep(20);
}
```

<br>
- **@DebugTrace:** method and constructor annotation that will print:

<img width="697" alt="gandalf_watch" src="https://cloud.githubusercontent.com/assets/1360604/9748615/40b2347e-5686-11e5-81c5-bae55250cb25.png">

```java
@DebugTrace
public void doSomethingOnMainThread() {
    try {
        Thread.sleep(20);
    } catch (InterruptedException e) {
        e.printStackTrace();
    }
}
```

<br>
- **MainLooper message queue dump:** at any point you can call ```Gandalf.dumpMainLooper(context)``` and will print the content of the queue:

<img width="1173" alt="gandalf_looper" src="https://cloud.githubusercontent.com/assets/1360604/9748663/a17acd98-5686-11e5-9ae3-0fb9e458d1b0.png">

```java
Gandalf.dumpMainLooper(context)
```


<br>
Enabling Gandalf
-----------------
To enable Gandalf, a gradle plugin must be applied in your ```build.gradle```:

```java
buildscript {
  repositories {
    mavenCentral()
  }
  dependencies {
    classpath "com.soundcloud.gandalf:gandalf-plugin:${latest_version}"
  }
}

apply plugin: 'com.android.application'
apply plugin: 'com.soundcloud.gandalf'
```


<br>
Under the hoods
-----------------

You can check this presentation:
https://docs.google.com/presentation/d/1ZZvzH-NcewP27LJWRVWemdcVXnZwX_jkT305BPQuAj8/edit#slide=id.gbbd9fc2e_113

<br>
Local Development
-----------------

Here are some useful Gradle commands for executing and installing the library:

 * `./setup_environment.sh` - One time execution command for installing gandalf library and dependencies.
 * `./gradlew installGandalfApi` - Installs Gandalf Api dependencies.
 * `./gradlew installGandalfRuntime` - Installs Gandalf Runtime dependencies.
 * `./gradlew installGandalfPlugin` - Installs Gandalf gradle plugin.
 * `./gradlew installGandalfExample` - Build and installs everything plus android application sample.
 * `./gradlew runUnitTests` - Run unit tests.
