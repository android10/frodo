package com.fernandocejas.example.frodo.sample;

import com.fernandocejas.frodo.annotation.RxLogSubscriber;
import rx.Subscriber;

@RxLogSubscriber
public class MySubscriberVoid extends Subscriber<Void> {
  @Override public void onCompleted() {
    //empty
  }

  @Override public void onError(Throwable e) {
    //empty
  }

  @Override public void onNext(Void aVoid) {
    //empty
  }
}
