package com.fernandocejas.frodo.internal;

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

  public void printObservableOnUnsubscribe(FrodoObservable.ObservableInfo observableInfo,
      String threadName) {
    final String message = messageBuilder.buildObservableOnUnsubscribeMessage(observableInfo, threadName);
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
