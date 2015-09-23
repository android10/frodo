package com.fernandocejas.frodo.internal;

import com.fernandocejas.frodo.joinpoint.FrodoJoinPoint;

public class MessageManager {

  private final MessageBuilder messageBuilder;
  private final DebugLog debugLog;

  public MessageManager() {
    this(new MessageBuilder(), new DebugLog());
  }

  public MessageManager(MessageBuilder messageBuilder, DebugLog debugLog) {
    this.messageBuilder = messageBuilder;
    this.debugLog = debugLog;
  }

  private void printMessage(String tag, String message) {
    debugLog.log(tag, message);
  }

  public void printTraceEnterMessage(FrodoJoinPoint joinPoint) {
    final String message = messageBuilder.buildTraceEnterMessage(joinPoint);
    printMessage(joinPoint.getClassSimpleName(), message);
  }

  public void printTraceExitMessage(FrodoJoinPoint joinPoint, Object returnValue,
      String executionTimeMillis) {
    final String message = messageBuilder.buildTraceExitMessage(joinPoint, returnValue,
        executionTimeMillis);
    printMessage(joinPoint.getClassSimpleName(), message);
  }

  public void printLogMessageEverything(FrodoJoinPoint joinPoint, Object returnValue,
      String executionTimeMillis) {
    final String message = messageBuilder.buildLogMessageEverything(joinPoint,
        returnValue, executionTimeMillis);
    printMessage(joinPoint.getClassSimpleName(), message);
  }

  public void printLogMessageSignature(FrodoJoinPoint joinPoint, Object returnValue) {
    final String message = messageBuilder.buildLogMessageSignature(joinPoint,
        returnValue);
    printMessage(joinPoint.getClassSimpleName(), message);
  }

  public void printLogMessageThread(FrodoJoinPoint joinPoint) {
    final String message = messageBuilder.buildLogMessageThread(joinPoint);
    printMessage(joinPoint.getClassSimpleName(), message);
  }

  public void printLogMessageTime(FrodoJoinPoint joinPoint, String executionTimeMillis) {
    final String message = messageBuilder.buildLogMessageTime(joinPoint,
        executionTimeMillis);
    printMessage(joinPoint.getClassSimpleName(), message);
  }

  public void printInspectStats(int activitiesCreated, int fragmentsCreated, int servicesCreated) {
    if (activitiesCreated > 0) {
      final String message = messageBuilder.buildInspectStatsMessage(
          MessageBuilder.STATS_MESSAGE_ACTIVITIES_CREATED, activitiesCreated);
      printMessage(MessageBuilder.STATS_MESSAGE_TAG, message);
    }
    if (fragmentsCreated > 0) {
      final String message = messageBuilder.buildInspectStatsMessage(
          MessageBuilder.STATS_MESSAGE_FRAGMENTS_CREATED, fragmentsCreated);
      printMessage(MessageBuilder.STATS_MESSAGE_TAG, message);
    }
    if (servicesCreated > 0) {
      final String message = messageBuilder.buildInspectStatsMessage(
          MessageBuilder.STATS_MESSAGE_SERVICES_CREATED, servicesCreated);
      printMessage(MessageBuilder.STATS_MESSAGE_TAG, message);
    }
  }

  public void printDumpMainLooper(FrodoJoinPoint joinPoint, String dump) {
    final String message = messageBuilder.buildDumpMainLooperMessage(dump);
    printMessage(joinPoint.getClassSimpleName(), message);
  }

  public void printDumpMainLooperException(FrodoJoinPoint joinPoint, Throwable throwable) {
    final String message = messageBuilder.buildDumpMainLooperExceptionMessage(throwable);
    printMessage(joinPoint.getClassSimpleName(), message);
  }

  public void printObservableInfo(FrodoObservable.ObservableInfo observableInfo) {
    final String message = messageBuilder.buildObservableInfoMessage(observableInfo);
    this.printMessage(observableInfo.getClassSimpleName(), message);
  }

  public void printObservableOnSubscribe(FrodoObservable.ObservableInfo observableInfo,
      String threadName) {
    final String message =
        messageBuilder.buildObservableOnSubscribeMessage(observableInfo, threadName);
    this.printMessage(observableInfo.getClassSimpleName(), message);
  }

  public <T> void printObservableOnNext(FrodoObservable.ObservableInfo observableInfo, T value) {
    final String message = messageBuilder.buildObservableOnNextMessage(observableInfo, value);
    this.printMessage(observableInfo.getClassSimpleName(), message);
  }

  public void printObservableOnError(FrodoObservable.ObservableInfo observableInfo,
      Throwable throwable) {
    final String message =
        messageBuilder.buildObservableOnErrorMessage(observableInfo, throwable.getMessage());
    this.printMessage(observableInfo.getClassSimpleName(), message);
  }

  public void printObservableOnCompleted(FrodoObservable.ObservableInfo observableInfo) {
    final String message = messageBuilder.buildObservableOnCompletedMessage(observableInfo);
    this.printMessage(observableInfo.getClassSimpleName(), message);
  }

  public void printObservableOnTerminate(FrodoObservable.ObservableInfo observableInfo,
      long executionTimeMillis,
      int emittedElements) {
    final String message = messageBuilder.buildObservableOnTerminateMessage(observableInfo,
        String.valueOf(executionTimeMillis), emittedElements);
    this.printMessage(observableInfo.getClassSimpleName(), message);
  }

  public void printObservableOnUnsubscribe(FrodoObservable.ObservableInfo observableInfo) {
    final String message = messageBuilder.buildObservableOnUnsubscribeMessage(observableInfo);
    this.printMessage(observableInfo.getClassSimpleName(), message);
  }

  public void printSubscriberOnStart(String subscriberName) {
    final String message = messageBuilder.buildSubscriberOnStartMessage(subscriberName);
    this.printMessage(subscriberName, message);
  }

  public void printSubscriberOnNext(String subscriberName, String value, String threadName) {
    final String message =
        messageBuilder.buildSubscriberOnNextMessage(subscriberName, value, threadName);
    this.printMessage(subscriberName, message);
  }

  public void printSubscriberOnError(String subscriberName, String error, long executionTimeMillis,
      int receivedItems) {
    final String message =
        messageBuilder.buildSubscriberOnErrorMessage(subscriberName, error, executionTimeMillis,
            receivedItems);
    this.printMessage(subscriberName, message);
  }

  public void printSubscriberOnCompleted(String subscriberName, long executionTimeMillis,
      int receivedItems) {
    final String message = messageBuilder.buildSubscriberOnCompletedMessage(subscriberName,
        executionTimeMillis, receivedItems);
    this.printMessage(subscriberName, message);
  }

  public void printSubscriberRequestedItems(String subscriberName, long requestedItems) {
    final String message =
        messageBuilder.buildSubscriberRequestedItemsMessage(subscriberName, requestedItems);
    this.printMessage(subscriberName, message);
  }

  public void printSubscriberUnsubscribe(String subscriberName) {
    final String message = messageBuilder.buildSubscriberUnsubscribeMessage(subscriberName);
    this.printMessage(subscriberName, message);
  }
}
