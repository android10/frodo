package com.fernandocejas.frodo.aspect;

import com.fernandocejas.frodo.joinpoint.TestJoinPoint;
import com.fernandocejas.frodo.joinpoint.TestProceedingJoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;
import rx.Observable;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

@RunWith(MockitoJUnitRunner.class)
public class LogObservableTest {

  @Test
  public void annotatedMethodMustCheckReturnType() {
    final ProceedingJoinPoint proceedingJoinPoint = mock(ProceedingJoinPoint.class);
    final MethodSignature methodSignature = mock(MethodSignature.class);

    given(proceedingJoinPoint.getSignature()).willReturn(methodSignature);
    given(methodSignature.getReturnType()).willReturn(Observable.class);

    assertThat(LogObservable.methodAnnotatedWithRxLogObservable(proceedingJoinPoint)).isTrue();
    verify(methodSignature).getReturnType();
    verifyNoMoreInteractions(methodSignature);
  }

  @Test
  public void shouldWeaveAroundMethodReturningObservable() {
    final TestJoinPoint joinPoint = new TestJoinPoint.Builder(this.getClass(), "toString")
        .withReturnType(Observable.class).build();
    final TestProceedingJoinPoint proceedingJoinPoint = new TestProceedingJoinPoint(joinPoint);

    assertThat(LogObservable.methodAnnotatedWithRxLogObservable(proceedingJoinPoint)).isTrue();
  }

  @Test
  public void shouldNotWeaveAroundMethodReturningOtherTypeThanObservable() {
    final TestJoinPoint joinPoint = new TestJoinPoint.Builder(this.getClass(), "toString")
        .withReturnType(this.getClass()).build();
    final TestProceedingJoinPoint proceedingJoinPoint = new TestProceedingJoinPoint(joinPoint);

    assertThat(LogObservable.methodAnnotatedWithRxLogObservable(proceedingJoinPoint)).isFalse();
  }
}