package com.fernandocejas.frodo.aspect;

import com.fernandocejas.frodo.annotation.RxLogObservable;
import com.fernandocejas.frodo.internal.observable.FrodoObservable;
import com.fernandocejas.frodo.internal.observable.LoggableObservableFactory;
import com.fernandocejas.frodo.internal.MessageManager;
import com.fernandocejas.frodo.internal.observable.ObservableInfo;
import com.fernandocejas.frodo.joinpoint.FrodoProceedingJoinPoint;
import java.lang.annotation.Annotation;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import rx.Observable;

@Aspect
public class LogObservable {
  private static final String METHOD =
      "execution(@com.fernandocejas.frodo.annotation.RxLogObservable * *(..)) && if()";

  @Pointcut(METHOD)
  public static boolean methodAnnotatedWithRxLogObservable(ProceedingJoinPoint joinPoint) {
    final FrodoProceedingJoinPoint frodoJoinPoint = new FrodoProceedingJoinPoint(joinPoint);
    final Annotation annotation = frodoJoinPoint.getAnnotation(RxLogObservable.class);
    return ((MethodSignature) joinPoint.getSignature()).getReturnType() == Observable.class
        && ((RxLogObservable)annotation).value() != RxLogObservable.Scope.NOTHING;
  }

  @Around("methodAnnotatedWithRxLogObservable(joinPoint)")
  public Object weaveAroundJoinPoint(ProceedingJoinPoint joinPoint) throws Throwable {
    final FrodoProceedingJoinPoint proceedingJoinPoint = new FrodoProceedingJoinPoint(joinPoint);
    final MessageManager messageManager = new MessageManager();
    final LoggableObservableFactory observableFactory =
        new LoggableObservableFactory(proceedingJoinPoint, messageManager,
            new ObservableInfo(proceedingJoinPoint));

    return new FrodoObservable(proceedingJoinPoint, messageManager,
        observableFactory).getObservable();
  }
}
