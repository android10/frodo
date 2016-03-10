package com.fernandocejas.frodo.internal;

import com.fernandocejas.frodo.internal.observable.ObservableInfo;

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

  public void printObservableInfo(ObservableInfo observableInfo) {
    final String message = messageBuilder.buildObservableInfoMessage(observableInfo);
    this.printMessage(observableInfo.getClassSimpleName(), message);
  }

  public void printObservableOnSubscribe(ObservableInfo observableInfo) {
    final String message = messageBuilder.buildObservableOnSubscribeMessage(observableInfo);
    this.printMessage(observableInfo.getClassSimpleName(), message);
  }

  public <T> void printObservableOnNextWithValue(ObservableInfo observableInfo, T value) {
    final String message =
        messageBuilder.buildObservableOnNextWithValueMessage(observableInfo, value);
    this.printMessage(observableInfo.getClassSimpleName(), message);
  }

  public void printObservableOnNext(ObservableInfo observableInfo) {
    final String message = messageBuilder.buildObservableOnNextMessage(observableInfo);
    this.printMessage(observableInfo.getClassSimpleName(), message);
  }

  public void printObservableOnError(ObservableInfo observableInfo,
      Throwable throwable) {
    final String message =
        messageBuilder.buildObservableOnErrorMessage(observableInfo, throwable.getMessage());
    this.printMessage(observableInfo.getClassSimpleName(), message);
  }

  public void printObservableOnCompleted(ObservableInfo observableInfo) {
    final String message = messageBuilder.buildObservableOnCompletedMessage(observableInfo);
    this.printMessage(observableInfo.getClassSimpleName(), message);
  }

  public void printObservableOnTerminate(ObservableInfo observableInfo) {
    final String message = messageBuilder.buildObservableOnTerminateMessage(observableInfo);
    this.printMessage(observableInfo.getClassSimpleName(), message);
  }

  public void printObservableOnUnsubscribe(ObservableInfo observableInfo) {
    final String message = messageBuilder.buildObservableOnUnsubscribeMessage(observableInfo);
    this.printMessage(observableInfo.getClassSimpleName(), message);
  }

  public void printSubscriberOnStart(String subscriberName) {
    final String message = messageBuilder.buildSubscriberOnStartMessage(subscriberName);
    this.printMessage(subscriberName, message);
  }

  public void printSubscriberOnNext(String subscriberName, Object value, String threadName) {
    final String message =
        messageBuilder.buildSubscriberOnNextMessage(subscriberName, value, threadName);
    this.printMessage(subscriberName, message);
  }

  public void printSubscriberOnError(String subscriberName, String error, long executionTimeMillis,
      int receivedItems) {
    final String itemTimeMessage =
        messageBuilder.buildSubscriberItemTimeMessage(subscriberName, executionTimeMillis,
            receivedItems);
    final String onErrorMessage =
        messageBuilder.buildSubscriberOnErrorMessage(subscriberName, error);
    this.printMessage(subscriberName, itemTimeMessage);
    this.printMessage(subscriberName, onErrorMessage);
  }

  public void printSubscriberOnCompleted(String subscriberName, long executionTimeMillis,
      int receivedItems) {
    final String itemTimeMessage =
        messageBuilder.buildSubscriberItemTimeMessage(subscriberName, executionTimeMillis,
            receivedItems);
    final String onCompleteMessage =
        messageBuilder.buildSubscriberOnCompletedMessage(subscriberName);
    this.printMessage(subscriberName, itemTimeMessage);
    this.printMessage(subscriberName, onCompleteMessage);
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

  public void printObservableItemTimeInfo(ObservableInfo observableInfo) {
    final String message = messageBuilder.buildObservableItemTimeInfoMessage(observableInfo);
    this.printMessage(observableInfo.getClassSimpleName(), message);
  }

  public void printObservableThreadInfo(ObservableInfo observableInfo) {
    if (observableInfo.getSubscribeOnThread().isPresent() ||
        observableInfo.getObserveOnThread().isPresent()) {
      final String message = messageBuilder.buildObservableThreadInfoMessage(observableInfo);
      this.printMessage(observableInfo.getClassSimpleName(), message);
    }
  }
}
