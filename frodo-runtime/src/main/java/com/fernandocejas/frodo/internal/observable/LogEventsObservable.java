package com.fernandocejas.frodo.internal.observable;

import com.fernandocejas.frodo.internal.MessageManager;
import com.fernandocejas.frodo.joinpoint.FrodoProceedingJoinPoint;
import rx.Observable;
import rx.functions.Action0;
import rx.functions.Action1;

@SuppressWarnings("unchecked") class LogEventsObservable extends LoggableObservable {
  LogEventsObservable(FrodoProceedingJoinPoint joinPoint,
      MessageManager messageManager, ObservableInfo observableInfo) {
    super(joinPoint, messageManager, observableInfo);
  }

  @Override <T> Observable<T> get(T type) throws Throwable {
    return ((Observable<T>) joinPoint.proceed())
        .doOnSubscribe(new Action0() {
          @Override
          public void call() {
            messageManager.printObservableOnSubscribe(observableInfo);
          }
        })
        .doOnNext(new Action1<T>() {
          @Override
          public void call(T value) {
            messageManager.printObservableOnNext(observableInfo);
          }
        })
        .doOnError(new Action1<Throwable>() {
          @Override
          public void call(Throwable throwable) {
            messageManager.printObservableOnError(observableInfo, throwable);
          }
        })
        .doOnCompleted(new Action0() {
          @Override
          public void call() {
            messageManager.printObservableOnCompleted(observableInfo);
          }
        })
        .doOnTerminate(new Action0() {
          @Override
          public void call() {
            messageManager.printObservableOnTerminate(observableInfo);
          }
        })
        .doOnUnsubscribe(new Action0() {
          @Override
          public void call() {
            messageManager.printObservableOnUnsubscribe(observableInfo);
          }
        });
  }
}
