package com.fernandocejas.frodo.internal;

import android.util.Log;
import com.fernandocejas.frodo.core.strings.Strings;
import com.fernandocejas.frodo.joinpoint.FrodoProceedingJoinPoint;
import rx.Notification;
import rx.Observable;
import rx.functions.Action0;
import rx.functions.Action1;

public class FrodoObservable {

  private final FrodoProceedingJoinPoint joinPoint;
  private final MessageManager messageManager;
  private final ObservableInfo observableInfo;

  private String observeOnThread = Strings.EMPTY;

  public FrodoObservable(FrodoProceedingJoinPoint joinPoint, MessageManager messageManager) {
    this.joinPoint = joinPoint;
    this.messageManager = messageManager;
    this.observableInfo = new ObservableInfo(joinPoint);
  }

  public rx.Observable getObservable() throws Throwable {
    messageManager.printObservableInfo(observableInfo);
    return logObservable(joinPoint.getGenericReturnTypes().get(0));
  }

  @SuppressWarnings("unchecked")
  private <T> rx.Observable<T> logObservable(T type) throws Throwable {
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
            messageManager.printObservableOnNext(observableInfo, value);
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
