package com.fernandocejas.frodo.joinpoint;

import org.aspectj.lang.ProceedingJoinPoint;

public class FrodoProceedingJoinPoint extends FrodoJoinPoint {

  private final ProceedingJoinPoint proceedingJoinPoint;

  public FrodoProceedingJoinPoint(ProceedingJoinPoint proceedingJoinPoint) {
    super(proceedingJoinPoint);
    this.proceedingJoinPoint = proceedingJoinPoint;
  }

  /**
   * {@link ProceedingJoinPoint#proceed()}
   */
  public Object proceed() throws Throwable {
    return proceedingJoinPoint.proceed();
  }

  /**
   * {@link ProceedingJoinPoint#proceed(Object[])}
   */
  public Object proceed(Object[] args) throws Throwable {
    return proceedingJoinPoint.proceed(args);
  }
}
