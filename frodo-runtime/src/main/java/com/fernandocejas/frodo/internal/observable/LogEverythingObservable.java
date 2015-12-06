package com.fernandocejas.frodo.internal.observable;

import com.fernandocejas.frodo.internal.Counter;
import com.fernandocejas.frodo.internal.MessageManager;
import com.fernandocejas.frodo.internal.StopWatch;
import com.fernandocejas.frodo.joinpoint.FrodoProceedingJoinPoint;
import rx.Notification;
import rx.Observable;
import rx.functions.Action0;
import rx.functions.Action1;

@SuppressWarnings("unchecked") class LogEverythingObservable extends LoggableObservable {

  LogEverythingObservable(FrodoProceedingJoinPoint joinPoint, MessageManager messageManager,
      ObservableInfo observableInfo) {
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
            messageManager.printObservableOnSubscribe(observableInfo);
          }
        })
        .doOnEach(new Action1<Notification<? super T>>() {
          @Override public void call(Notification<? super T> notification) {
            if (!observableInfo.getSubscribeOnThread().isPresent()
                && (notification.isOnNext() || notification.isOnError())) {
              observableInfo.setSubscribeOnThread(Thread.currentThread().getName());
            }
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
        .doOnCompleted(new Action0() {
          @Override
          public void call() {
            messageManager.printObservableOnCompleted(observableInfo);
          }
        })
        .doOnTerminate(new Action0() {
          @Override
          public void call() {
            stopWatch.stop();
            observableInfo.setTotalExecutionTime(stopWatch.getTotalTimeMillis());
            observableInfo.setTotalEmittedItems(emittedItems.tally());
            messageManager.printObservableOnTerminate(observableInfo);
            messageManager.printObservableItemTimeInfo(observableInfo);
          }
        })
        .doOnUnsubscribe(new Action0() {
          @Override
          public void call() {
            if (!observableInfo.getObserveOnThread().isPresent()) {
              observableInfo.setObserveOnThread(Thread.currentThread().getName());
            }
            messageManager.printObservableThreadInfo(observableInfo);
            messageManager.printObservableOnUnsubscribe(observableInfo);
          }
        });
  }
}
