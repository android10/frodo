package com.fernandocejas.frodo.internal;

import com.fernandocejas.frodo.joinpoint.FrodoJoinPoint;

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

  FrodoJoinPoint getJoinPoint() {
    return joinPoint;
  }
}
