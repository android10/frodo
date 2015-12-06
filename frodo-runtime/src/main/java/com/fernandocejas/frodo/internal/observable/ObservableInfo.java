package com.fernandocejas.frodo.internal.observable;

import com.fernandocejas.frodo.core.optional.Optional;
import com.fernandocejas.frodo.joinpoint.FrodoJoinPoint;

public class ObservableInfo {
  private final FrodoJoinPoint joinPoint;

  private String subscribeOnThread;
  private String observeOnThread;
  private long totalExecutionTime;
  private int totalEmittedItems;

  public ObservableInfo(FrodoJoinPoint joinPoint) {
    this.joinPoint = joinPoint;
  }

  public String getClassSimpleName() {
    return joinPoint.getClassSimpleName();
  }

  public String getMethodName() {
    return joinPoint.getMethodName();
  }

  public FrodoJoinPoint getJoinPoint() {
    return joinPoint;
  }

  public Optional<String> getSubscribeOnThread() {
    return Optional.fromNullable(subscribeOnThread);
  }

  public Optional<String> getObserveOnThread() {
    return Optional.fromNullable(observeOnThread);
  }

  public Optional<Long> getTotalExecutionTime() {
    if (totalExecutionTime == 0) {
      return Optional.absent();
    }
    return Optional.of(totalExecutionTime);
  }

  public Optional<Integer> getTotalEmittedItems() {
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
