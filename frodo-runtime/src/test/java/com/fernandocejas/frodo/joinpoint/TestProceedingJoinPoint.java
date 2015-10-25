package com.fernandocejas.frodo.joinpoint;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.reflect.MethodSignature;
import org.aspectj.lang.reflect.SourceLocation;
import org.aspectj.runtime.internal.AroundClosure;

public class TestProceedingJoinPoint implements ProceedingJoinPoint {

  private final TestJoinPoint testJoinPoint;

  public TestProceedingJoinPoint(TestJoinPoint testJoinPoint) {
    this.testJoinPoint = testJoinPoint;
  }

  @Override public void set$AroundClosure(AroundClosure arc) {
    //do nothing
  }

  @Override public Object proceed() throws Throwable {
    return ((MethodSignature) testJoinPoint.getSignature()).getReturnType();
  }

  @Override public Object proceed(Object[] args) throws Throwable {
    return ((MethodSignature) testJoinPoint.getSignature()).getReturnType();
  }

  @Override public String toShortString() {
    return testJoinPoint.toShortString();
  }

  @Override public String toLongString() {
    return testJoinPoint.toLongString();
  }

  @Override public Object getThis() {
    return this;
  }

  @Override public Object getTarget() {
    return testJoinPoint.getTarget();
  }

  @Override public Object[] getArgs() {
    return testJoinPoint.getArgs();
  }

  @Override public Signature getSignature() {
    return testJoinPoint.getSignature();
  }

  @Override public SourceLocation getSourceLocation() {
    return testJoinPoint.getSourceLocation();
  }

  @Override public String getKind() {
    return testJoinPoint.getKind();
  }

  @Override public StaticPart getStaticPart() {
    return testJoinPoint.getStaticPart();
  }
}
