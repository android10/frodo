package com.fernandocejas.example.frodo.sample;

import com.fernandocejas.frodo.annotation.RxLogSubscriber;
import rx.Subscriber;

@RxLogSubscriber
public class MySubscriber extends Subscriber<String> {
  @Override
  public void onNext(String value) {
    //empty
  }

  @Override
  public void onError(Throwable throwable) {
    //empty
  }

  @Override
  public void onCompleted() {
    //empty
  }
}
