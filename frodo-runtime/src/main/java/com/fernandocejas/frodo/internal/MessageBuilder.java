package com.fernandocejas.frodo.internal;

import com.fernandocejas.frodo.core.optional.Optional;
import com.fernandocejas.frodo.internal.observable.ObservableInfo;
import com.fernandocejas.frodo.joinpoint.FrodoJoinPoint;
import java.util.List;

/**
 * Class used to build different messages that will be shown in debug mode
 */
class MessageBuilder {

  private static final String LOG_START = "@";
  private static final String SEPARATOR = " :: ";
  private static final String METHOD_SEPARATOR = "#";
  private static final String VALUE_SEPARATOR = " -> ";
  private static final String TEXT_ENCLOSING_SYMBOL = "'";
  private static final String LOG_ENCLOSING_OPEN = "[";
  private static final String LOG_ENCLOSING_CLOSE = "]";
  private static final String LIBRARY_LABEL = "Frodo => ";
  private static final String CLASS_LABEL = LOG_START + "InClass" + VALUE_SEPARATOR;
  private static final String METHOD_LABEL = LOG_START + "Method" + VALUE_SEPARATOR;
  private static final String TIME_LABEL = LOG_START + "Time" + VALUE_SEPARATOR;
  private static final String TIME_MILLIS = " ms";
  private static final String OBSERVABLE_LABEL = LOG_START + "Observable";
  private static final String EMITTED_ELEMENTS_LABEL = LOG_START + "Emitted" + VALUE_SEPARATOR;
  private static final String LABEL_OBSERVABLE_ON_SUBSCRIBE = "onSubscribe()";
  private static final String LABEL_OBSERVABLE_ON_NEXT = "onNext()";
  private static final String LABEL_OBSERVABLE_ON_ERROR = "onError()";
  private static final String LABEL_OBSERVABLE_ON_COMPLETED = "onCompleted()";
  private static final String LABEL_OBSERVABLE_ON_TERMINATE = "onTerminate()";
  private static final String LABEL_OBSERVABLE_ON_UNSUBSCRIBE = "onUnsubscribe()";
  private static final String LABEL_OBSERVABLE_SUBSCRIBE_ON =
      LOG_START + "SubscribeOn" + VALUE_SEPARATOR;
  private static final String SUBSCRIBER_LABEL = LOG_START + "Subscriber";
  private static final String LABEL_SUBSCRIBER_ON_START = "onStart()";
  private static final String LABEL_SUBSCRIBER_ON_NEXT = "onNext()";
  private static final String LABEL_SUBSCRIBER_ON_ERROR = "onError()";
  private static final String LABEL_SUBSCRIBER_ON_COMPLETED = "onCompleted()";
  private static final String LABEL_SUBSCRIBER_UN_SUBSCRIBE = "unSubscribe()";
  private static final String LABEL_SUBSCRIBER_OBSERVE_ON =
      LOG_START + "ObserveOn" + VALUE_SEPARATOR;
  private static final String REQUESTED_ELEMENTS_LABEL = LOG_START + "Requested" + VALUE_SEPARATOR;
  private static final String RECEIVED_ELEMENTS_LABEL = LOG_START + "Received" + VALUE_SEPARATOR;
  private static final String LABEL_ELEMENT_SINGULAR = " element";
  private static final String LABEL_ELEMENT_PLURAL = " elements";
  private static final String LABEL_MESSAGE_NULL_OBSERVABLES = "You received a null observable";

  MessageBuilder() {}

  String buildObservableInfoMessage(ObservableInfo observableInfo) {
    final FrodoJoinPoint joinPoint = observableInfo.getJoinPoint();
    final StringBuilder message = buildObservableSB();
    message.append(SEPARATOR);
    message.append(CLASS_LABEL);
    message.append(observableInfo.getClassSimpleName());
    message.append(SEPARATOR);
    message.append(METHOD_LABEL);
    message.append(observableInfo.getMethodName());
    message.append(buildMethodSignatureWithValues(joinPoint));
    message.append(LOG_ENCLOSING_CLOSE);

    return message.toString();
  }

  String buildObservableOnSubscribeMessage(ObservableInfo observableInfo) {
    final StringBuilder message = buildObservableSB();
    message.append(METHOD_SEPARATOR);
    message.append(observableInfo.getMethodName());
    message.append(VALUE_SEPARATOR);
    message.append(LABEL_OBSERVABLE_ON_SUBSCRIBE);
    message.append(LOG_ENCLOSING_CLOSE);

    return message.toString();
  }

  <T> String buildObservableOnNextWithValueMessage(ObservableInfo observableInfo, T value) {
    final StringBuilder message = buildObservableSB();
    message.append(METHOD_SEPARATOR);
    message.append(observableInfo.getMethodName());
    message.append(VALUE_SEPARATOR);
    message.append(LABEL_OBSERVABLE_ON_NEXT);
    message.append(VALUE_SEPARATOR);
    message.append(String.valueOf(value));
    message.append(LOG_ENCLOSING_CLOSE);

    return message.toString();
  }

  String buildObservableOnNextMessage(ObservableInfo observableInfo) {
    final StringBuilder message = buildObservableSB();
    message.append(METHOD_SEPARATOR);
    message.append(observableInfo.getMethodName());
    message.append(VALUE_SEPARATOR);
    message.append(LABEL_OBSERVABLE_ON_NEXT);
    message.append(LOG_ENCLOSING_CLOSE);

    return message.toString();
  }

  String buildObservableOnErrorMessage(ObservableInfo observableInfo, String errorMessage) {
    final StringBuilder message = buildObservableSB();
    message.append(METHOD_SEPARATOR);
    message.append(observableInfo.getMethodName());
    message.append(VALUE_SEPARATOR);
    message.append(LABEL_OBSERVABLE_ON_ERROR);
    message.append(VALUE_SEPARATOR);
    message.append(TEXT_ENCLOSING_SYMBOL);
    message.append(errorMessage);
    message.append(TEXT_ENCLOSING_SYMBOL);
    message.append(LOG_ENCLOSING_CLOSE);

    return message.toString();
  }

  String buildObservableOnCompletedMessage(ObservableInfo observableInfo) {
    final StringBuilder message = buildObservableSB();
    message.append(METHOD_SEPARATOR);
    message.append(observableInfo.getMethodName());
    message.append(VALUE_SEPARATOR);
    message.append(LABEL_OBSERVABLE_ON_COMPLETED);
    message.append(LOG_ENCLOSING_CLOSE);

    return message.toString();
  }

  String buildObservableOnTerminateMessage(ObservableInfo observableInfo) {
    final StringBuilder message = buildObservableSB();
    message.append(METHOD_SEPARATOR);
    message.append(observableInfo.getMethodName());
    message.append(VALUE_SEPARATOR);
    message.append(LABEL_OBSERVABLE_ON_TERMINATE);
    message.append(LOG_ENCLOSING_CLOSE);

    return message.toString();
  }

  String buildObservableOnUnsubscribeMessage(ObservableInfo observableInfo) {
    final StringBuilder message = buildObservableSB();
    message.append(METHOD_SEPARATOR);
    message.append(observableInfo.getMethodName());
    message.append(VALUE_SEPARATOR);
    message.append(LABEL_OBSERVABLE_ON_UNSUBSCRIBE);
    message.append(LOG_ENCLOSING_CLOSE);

    return message.toString();
  }

  String buildSubscriberOnStartMessage(String subscriberName) {
    final StringBuilder message = buildSubscriberSB();
    message.append(SEPARATOR);
    message.append(subscriberName);
    message.append(VALUE_SEPARATOR);
    message.append(LABEL_SUBSCRIBER_ON_START);
    message.append(LOG_ENCLOSING_CLOSE);

    return message.toString();
  }

  String buildSubscriberOnNextMessage(String subscriberName, Object value, String threadName) {
    final StringBuilder message = buildSubscriberSB();
    message.append(SEPARATOR);
    message.append(subscriberName);
    message.append(VALUE_SEPARATOR);
    message.append(LABEL_SUBSCRIBER_ON_NEXT);
    message.append(VALUE_SEPARATOR);
    message.append(value != null ? value.toString() : LABEL_MESSAGE_NULL_OBSERVABLES);
    message.append(SEPARATOR);
    message.append(LABEL_SUBSCRIBER_OBSERVE_ON);
    message.append(threadName);
    message.append(LOG_ENCLOSING_CLOSE);

    return message.toString();
  }

  String buildSubscriberOnErrorMessage(String subscriberName, String error) {
    final StringBuilder message = buildSubscriberSB();
    message.append(SEPARATOR);
    message.append(subscriberName);
    message.append(VALUE_SEPARATOR);
    message.append(LABEL_SUBSCRIBER_ON_ERROR);
    message.append(VALUE_SEPARATOR);
    message.append(error);
    message.append(LOG_ENCLOSING_CLOSE);

    return message.toString();
  }

  String buildSubscriberOnCompletedMessage(String subscriberName) {
    final StringBuilder message = buildSubscriberSB();
    message.append(SEPARATOR);
    message.append(subscriberName);
    message.append(VALUE_SEPARATOR);
    message.append(LABEL_SUBSCRIBER_ON_COMPLETED);
    message.append(LOG_ENCLOSING_CLOSE);

    return message.toString();
  }

  String buildSubscriberItemTimeMessage(String subscriberName, long executionTimeMillis,
      int receivedItems) {
    final StringBuilder message = buildSubscriberSB();
    message.append(SEPARATOR);
    message.append(subscriberName);
    message.append(VALUE_SEPARATOR);
    message.append(RECEIVED_ELEMENTS_LABEL);
    message.append(receivedItems);
    message.append(receivedItems == 1 ? LABEL_ELEMENT_SINGULAR : LABEL_ELEMENT_PLURAL);
    message.append(SEPARATOR);
    message.append(TIME_LABEL);
    message.append(executionTimeMillis);
    message.append(TIME_MILLIS);
    message.append(LOG_ENCLOSING_CLOSE);

    return message.toString();
  }

  String buildSubscriberRequestedItemsMessage(String subscriberName, long requestedItems) {
    final StringBuilder message = buildSubscriberSB();
    message.append(SEPARATOR);
    message.append(subscriberName);
    message.append(VALUE_SEPARATOR);
    message.append(REQUESTED_ELEMENTS_LABEL);
    message.append(requestedItems);
    message.append(requestedItems == 1 ? LABEL_ELEMENT_SINGULAR : LABEL_ELEMENT_PLURAL);
    message.append(LOG_ENCLOSING_CLOSE);

    return message.toString();
  }

  String buildSubscriberUnsubscribeMessage(String subscriberName) {
    final StringBuilder message = buildSubscriberSB();
    message.append(SEPARATOR);
    message.append(subscriberName);
    message.append(VALUE_SEPARATOR);
    message.append(LABEL_SUBSCRIBER_UN_SUBSCRIBE);
    message.append(LOG_ENCLOSING_CLOSE);

    return message.toString();
  }

  String buildObservableItemTimeInfoMessage(ObservableInfo observableInfo) {
    final int totalEmittedItems = observableInfo.getTotalEmittedItems().or(0);
    final long totalExecutionTime = observableInfo.getTotalExecutionTime().or(0L);
    final StringBuilder message = buildObservableSB();
    message.append(METHOD_SEPARATOR);
    message.append(observableInfo.getMethodName());
    message.append(VALUE_SEPARATOR);
    message.append(EMITTED_ELEMENTS_LABEL);
    message.append(totalEmittedItems);
    message.append(totalEmittedItems == 1 ? LABEL_ELEMENT_SINGULAR : LABEL_ELEMENT_PLURAL);
    message.append(SEPARATOR);
    message.append(TIME_LABEL);
    message.append(totalExecutionTime);
    message.append(TIME_MILLIS);
    message.append(LOG_ENCLOSING_CLOSE);

    return message.toString();
  }

  String buildObservableThreadInfoMessage(ObservableInfo observableInfo) {
    final Optional<String> subscribeOnThread = observableInfo.getSubscribeOnThread();
    final Optional<String> observeOnThread = observableInfo.getObserveOnThread();
    final StringBuilder message = buildObservableSB();
    message.append(METHOD_SEPARATOR);
    message.append(observableInfo.getMethodName());
    message.append(VALUE_SEPARATOR);
    if (subscribeOnThread.isPresent()) {
      message.append(LABEL_OBSERVABLE_SUBSCRIBE_ON);
      message.append(subscribeOnThread.get());
    }
    if (observeOnThread.isPresent()) {
      message.append(SEPARATOR);
      message.append(LABEL_SUBSCRIBER_OBSERVE_ON);
      message.append(observeOnThread.get());
    }
    message.append(LOG_ENCLOSING_CLOSE);

    return message.toString();
  }

  private StringBuilder buildSubscriberSB() {
    final int avgStringSize = 75;
    final StringBuilder message = new StringBuilder(avgStringSize + LIBRARY_LABEL.length());
    message.append(LIBRARY_LABEL);
    message.append(LOG_ENCLOSING_OPEN);
    message.append(SUBSCRIBER_LABEL);
    return message;
  }

  private StringBuilder buildObservableSB() {
    final int avgStringSize = 75;
    final StringBuilder message = new StringBuilder(avgStringSize + LIBRARY_LABEL.length());
    message.append(LIBRARY_LABEL);
    message.append(LOG_ENCLOSING_OPEN);
    message.append(OBSERVABLE_LABEL);
    return message;
  }

  private String buildMethodSignatureWithValues(FrodoJoinPoint joinPoint) {
    final int avg = 30;
    final StringBuilder stringBuilder = new StringBuilder(avg + joinPoint.getMethodName().length());
    stringBuilder.append("(");
    List<String> methodParamNames = joinPoint.getMethodParamNamesList();
    if (methodParamNames != null && !methodParamNames.isEmpty()) {
      for (int i = 0; i < joinPoint.getMethodParamNamesList().size(); i++) {
        stringBuilder.append(methodParamNames.get(i));
        stringBuilder.append("=");
        stringBuilder.append("'");
        stringBuilder.append(String.valueOf(joinPoint.getMethodParamValuesList().get(i)));
        stringBuilder.append("'");
        if ((i != methodParamNames.size() - 1)) {
          stringBuilder.append(", ");
        }
      }
    }
    stringBuilder.append(")");

    return stringBuilder.toString();
  }
}
