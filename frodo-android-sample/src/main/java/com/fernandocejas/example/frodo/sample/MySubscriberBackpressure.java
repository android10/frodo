package com.fernandocejas.example.frodo.sample;

import com.fernandocejas.frodo.annotation.RxLogSubscriber;
import rx.Subscriber;

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
