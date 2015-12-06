package com.fernandocejas.frodo.internal.observable;

import com.fernandocejas.frodo.annotation.RxLogObservable;
import com.fernandocejas.frodo.internal.MessageManager;
import com.fernandocejas.frodo.joinpoint.FrodoProceedingJoinPoint;
import java.lang.annotation.Annotation;

@SuppressWarnings("unchecked") public class LoggableObservableFactory {

  private final FrodoProceedingJoinPoint joinPoint;
  private final MessageManager messageManager;
  private final ObservableInfo observableInfo;

  public LoggableObservableFactory(FrodoProceedingJoinPoint joinPoint,
      MessageManager messageManager, ObservableInfo observableInfo) {
    this.joinPoint = joinPoint;
    this.messageManager = messageManager;
    this.observableInfo = observableInfo;
  }

  LoggableObservable create(Annotation annotation) {
    final Class observableType = joinPoint.getGenericReturnTypes().get(0);
    if (annotation != null) {
      switch (((RxLogObservable) annotation).value()) {
        case NOTHING:
          return new LogNothingObservable(joinPoint, messageManager, observableInfo);
        case STREAM:
          return new LogStreamObservable(joinPoint, messageManager, observableInfo);
        case SCHEDULERS:
          return new LogSchedulersObservable(joinPoint, messageManager, observableInfo);
        case EVENTS:
          return new LogEventsObservable(joinPoint, messageManager, observableInfo);
        case EVERYTHING:
        default:
          return new LogEverythingObservable(joinPoint, messageManager, observableInfo);
      }
    } else {
      return new LogNothingObservable(joinPoint, messageManager, observableInfo);
    }
  }
}
