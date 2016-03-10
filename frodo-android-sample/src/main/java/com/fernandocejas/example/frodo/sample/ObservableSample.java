package com.fernandocejas.example.frodo.sample;

import android.view.View;
import com.fernandocejas.frodo.annotation.RxLogObservable;
import java.util.Arrays;
import java.util.List;
import rx.Observable;
import rx.Subscriber;
import rx.functions.Func0;
import rx.schedulers.Schedulers;

import static com.fernandocejas.frodo.annotation.RxLogObservable.Scope.EVENTS;
import static com.fernandocejas.frodo.annotation.RxLogObservable.Scope.EVERYTHING;
import static com.fernandocejas.frodo.annotation.RxLogObservable.Scope.NOTHING;
import static com.fernandocejas.frodo.annotation.RxLogObservable.Scope.SCHEDULERS;
import static com.fernandocejas.frodo.annotation.RxLogObservable.Scope.STREAM;

public class ObservableSample {
  public ObservableSample() {
  }

  @RxLogObservable(EVERYTHING)
  public Observable<Integer> numbers() {
    return Observable.just(1, 2);
  }

  @RxLogObservable
  public Observable<Integer> moreNumbers() {
    return Observable.just(1, 2, 3, 4);
  }

  @RxLogObservable(STREAM)
  public Observable<String> names() {
    return Observable.just("Fernando", "Silvia");
  }

  @RxLogObservable
  public Observable<String> error() {
    return Observable.error(new IllegalArgumentException("My error"));
  }

  @RxLogObservable(SCHEDULERS)
  public Observable<List<MyDummyClass>> list() {
    return Observable.just(buildDummyList());
  }

  @RxLogObservable(EVENTS)
  public Observable<String> stringItemWithDefer() {
    return Observable.defer(new Func0<Observable<String>>() {
      @Override public Observable<String> call() {
        return Observable.create(new Observable.OnSubscribe<String>() {
          @Override public void call(Subscriber<? super String> subscriber) {
            try {
              subscriber.onNext("String item Three");
              subscriber.onCompleted();
            } catch (Exception e) {
              subscriber.onError(e);
            }
          }
        }).subscribeOn(Schedulers.computation());
      }
    });
  }

  /**
   * Nothing should happen here when annotating this method with {@link RxLogObservable}
   * because it does not returns an {@link Observable}.
   */
  @RxLogObservable(NOTHING)
  public List<MyDummyClass> buildDummyList() {
    return Arrays.asList(new MyDummyClass("Batman"), new MyDummyClass("Superman"));
  }

  @RxLogObservable
  public Observable<String> strings() {
    return Observable.just("Hello", "My", "Name", "Is", "Fernando");
  }

  public Observable<String> stringsWithError() {
    return Observable.error(new IllegalArgumentException("My Subscriber error"));
  }

  @RxLogObservable
  public Observable<Void> doSomething(View view) {
    return Observable.just(null);
  }

  @RxLogObservable
  public Observable<String> sendNull() {
    return Observable.just(null);
  }

  @RxLogObservable
  public Observable<Void> doNothing() {
    return Observable.empty();
  }

  public Observable<Integer> numbersBackpressure() {
    return Observable.create(new Observable.OnSubscribe<Integer>() {
      @Override
      public void call(Subscriber<? super Integer> subscriber) {
        try {
          if (!subscriber.isUnsubscribed()) {
            for (int i = 1; i < 10000; i++) {
              subscriber.onNext(i);
            }
            subscriber.onCompleted();
          }
        } catch (Exception e) {
          subscriber.onError(e);
        }
      }
    });
  }

  public static final class MyDummyClass {
    private final String name;

    MyDummyClass(String name) {
      this.name = name;
    }

    @Override
    public String toString() {
      return "Name: " + name;
    }
  }
}
