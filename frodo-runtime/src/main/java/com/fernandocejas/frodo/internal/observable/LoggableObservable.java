package com.fernandocejas.frodo.internal.observable;

import com.fernandocejas.frodo.core.annotations.VisibleForTesting;
import com.fernandocejas.frodo.internal.MessageManager;
import com.fernandocejas.frodo.joinpoint.FrodoProceedingJoinPoint;

abstract class LoggableObservable {

  final FrodoProceedingJoinPoint joinPoint;
  final MessageManager messageManager;
  final ObservableInfo observableInfo;

  LoggableObservable(FrodoProceedingJoinPoint joinPoint, MessageManager messageManager,
      ObservableInfo observableInfo) {
    this.joinPoint = joinPoint;
    this.messageManager = messageManager;
    this.observableInfo = new ObservableInfo(joinPoint);
  }

  abstract <T> rx.Observable<T> get(T type) throws Throwable;

  @VisibleForTesting ObservableInfo getInfo() {
    return observableInfo;
  }
}
