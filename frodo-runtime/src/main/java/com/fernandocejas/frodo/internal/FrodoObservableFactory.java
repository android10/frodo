package com.fernandocejas.frodo.internal;

import com.fernandocejas.frodo.joinpoint.FrodoProceedingJoinPoint;

class FrodoObservableFactory {

  private final FrodoProceedingJoinPoint joinPoint;

  FrodoObservableFactory(FrodoProceedingJoinPoint joinPoint) {
    this.joinPoint = joinPoint;
  }

  <T> rx.Observable<T> logStreamObservable(T type) {
    return null;
  }
}
