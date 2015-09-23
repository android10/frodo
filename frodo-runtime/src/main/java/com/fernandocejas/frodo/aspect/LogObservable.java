package com.fernandocejas.frodo.aspect;

import com.fernandocejas.frodo.internal.FrodoObservable;
import com.fernandocejas.frodo.internal.MessageManager;
import com.fernandocejas.frodo.joinpoint.FrodoProceedingJoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import rx.Observable;

@Aspect
public class LogObservable {
  private static final String METHOD =
      "execution(@RxLogObservable * *(..)) && if()";

  @Pointcut(METHOD)
  public static boolean methodAnnotatedWithRxLogObservable(ProceedingJoinPoint joinPoint) {
    return ((MethodSignature) joinPoint.getSignature()).getReturnType() == Observable.class;
  }

  @Around("methodAnnotatedWithRxLogObservable(joinPoint)")
  public Object weaveAroundJoinPoint(ProceedingJoinPoint joinPoint) throws Throwable {
    return new FrodoObservable(new FrodoProceedingJoinPoint(joinPoint),
        new MessageManager()).getObservable();
  }
}
