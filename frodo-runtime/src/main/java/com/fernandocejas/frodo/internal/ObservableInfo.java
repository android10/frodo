package com.fernandocejas.frodo.internal;

import com.fernandocejas.frodo.core.optional.Optional;
import com.fernandocejas.frodo.joinpoint.FrodoJoinPoint;

class ObservableInfo {
  private final FrodoJoinPoint joinPoint;

  private String subscribeOnThread;
  private String observeOnThread;
  private long totalExecutionTime;
  private int totalEmittedItems;

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

  Optional<String> getSubscribeOnThread() {
    return Optional.fromNullable(subscribeOnThread);
  }

  Optional<String> getObserveOnThread() {
    return Optional.fromNullable(observeOnThread);
  }

  Optional<Long> getTotalExecutionTime() {
    if (totalExecutionTime == 0) {
      return Optional.absent();
    }
    return Optional.of(totalExecutionTime);
  }

  Optional<Integer> getTotalEmittedItems() {
    if (totalEmittedItems == 0) {
      return Optional.absent();
    }
    return Optional.of(totalEmittedItems);
  }

  void setSubscribeOnThread(String subscribeOnThread) {
    this.subscribeOnThread = subscribeOnThread;
  }

  void setObserveOnThread(String observeOnThread) {
    this.observeOnThread = observeOnThread;
  }

  void setTotalExecutionTime(long totalExecutionTime) {
    this.totalExecutionTime = totalExecutionTime;
  }

  void setTotalEmittedItems(int totalEmittedItems) {
    this.totalEmittedItems = totalEmittedItems;
  }
}
