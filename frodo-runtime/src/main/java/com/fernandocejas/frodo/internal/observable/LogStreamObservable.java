package com.fernandocejas.frodo.internal.observable;

import com.fernandocejas.frodo.internal.Counter;
import com.fernandocejas.frodo.internal.MessageManager;
import com.fernandocejas.frodo.internal.StopWatch;
import com.fernandocejas.frodo.joinpoint.FrodoProceedingJoinPoint;
import rx.Observable;
import rx.functions.Action0;
import rx.functions.Action1;

@SuppressWarnings("unchecked") class LogStreamObservable extends LoggableObservable {
  LogStreamObservable(FrodoProceedingJoinPoint joinPoint,
      MessageManager messageManager, ObservableInfo observableInfo) {
    super(joinPoint, messageManager, observableInfo);
  }

  @Override <T> Observable<T> get(T type) throws Throwable {
    final StopWatch stopWatch = new StopWatch();
    final Counter emittedItems = new Counter(joinPoint.getMethodName());
    return ((Observable<T>) joinPoint.proceed())
        .doOnSubscribe(new Action0() {
          @Override
          public void call() {
            stopWatch.start();
          }
        })
        .doOnNext(new Action1<T>() {
          @Override
          public void call(T value) {
            emittedItems.increment();
            messageManager.printObservableOnNextWithValue(observableInfo, value);
          }
        })
        .doOnError(new Action1<Throwable>() {
          @Override
          public void call(Throwable throwable) {
            messageManager.printObservableOnError(observableInfo, throwable);
          }
        })
        .doOnTerminate(new Action0() {
          @Override
          public void call() {
            stopWatch.stop();
            observableInfo.setTotalExecutionTime(stopWatch.getTotalTimeMillis());
            observableInfo.setTotalEmittedItems(emittedItems.tally());
            messageManager.printObservableItemTimeInfo(observableInfo);
          }
        });
  }
}
