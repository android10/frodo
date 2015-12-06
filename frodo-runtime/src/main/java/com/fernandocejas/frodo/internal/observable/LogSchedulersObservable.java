package com.fernandocejas.frodo.internal.observable;

import com.fernandocejas.frodo.internal.MessageManager;
import com.fernandocejas.frodo.joinpoint.FrodoProceedingJoinPoint;
import rx.Notification;
import rx.Observable;
import rx.functions.Action0;
import rx.functions.Action1;

@SuppressWarnings("unchecked") class LogSchedulersObservable extends LoggableObservable {
  LogSchedulersObservable(FrodoProceedingJoinPoint joinPoint,
      MessageManager messageManager, ObservableInfo observableInfo) {
    super(joinPoint, messageManager, observableInfo);
  }

  @Override <T> Observable<T> get(T type) throws Throwable {
    return ((Observable<T>) joinPoint.proceed())
        .doOnEach(new Action1<Notification<? super T>>() {
          @Override public void call(Notification<? super T> notification) {
            if (!observableInfo.getSubscribeOnThread().isPresent()
                && (notification.isOnNext() || notification.isOnError())) {
              observableInfo.setSubscribeOnThread(Thread.currentThread().getName());
            }
          }
        })
        .doOnUnsubscribe(new Action0() {
          @Override
          public void call() {
            if (!observableInfo.getObserveOnThread().isPresent()) {
              observableInfo.setObserveOnThread(Thread.currentThread().getName());
            }
            messageManager.printObservableThreadInfo(observableInfo);
          }
        });
  }
}
