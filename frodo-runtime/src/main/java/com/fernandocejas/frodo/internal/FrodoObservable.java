package com.fernandocejas.frodo.internal;

import com.fernandocejas.frodo.joinpoint.FrodoJoinPoint;
import com.fernandocejas.frodo.joinpoint.FrodoProceedingJoinPoint;
import rx.functions.Action0;
import rx.functions.Action1;

public class FrodoObservable {

  private final FrodoProceedingJoinPoint joinPoint;
  private final MessageManager messageManager;
  private final ObservableInfo observableInfo;

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
    final Counter emittedElements = new Counter(joinPoint.getMethodName());
    return ((rx.Observable<T>) joinPoint.proceed())
        .doOnSubscribe(new Action0() {
          @Override
          public void call() {
            stopWatch.start();
            messageManager.printObservableOnSubscribe(observableInfo,
                Thread.currentThread().getName());
          }
        })
        .doOnNext(new Action1<T>() {
          @Override
          public void call(T value) {
            emittedElements.increment();
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
        .doOnUnsubscribe(new Action0() {
          @Override
          public void call() {
            messageManager.printObservableOnUnsubscribe(observableInfo);
          }
        })
        .doOnTerminate(new Action0() {
          @Override
          public void call() {
            stopWatch.stop();
            messageManager.printObservableOnTerminate(observableInfo,
                stopWatch.getTotalTimeMillis(),
                emittedElements.tally());
          }
        });
  }

  class ObservableInfo {
    private final FrodoJoinPoint joinPoint;

    ObservableInfo(FrodoJoinPoint joinPoint) {
      this.joinPoint = joinPoint;
    }

    String getClassSimpleName() {
      return joinPoint.getClassSimpleName();
    }

    String getMethodName() {
      return joinPoint.getMethodName();
    }

    public FrodoJoinPoint getJoinPoint() {
      return joinPoint;
    }
  }
}
