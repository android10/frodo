package com.fernandocejas.frodo.aspect;

import com.fernandocejas.frodo.joinpoint.TestJoinPoint;
import com.fernandocejas.frodo.joinpoint.TestProceedingJoinPoint;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class LogObservableTest {

  @Test
  public void shouldNotWeaveAroundMethodReturningOtherTypeThanObservable() {
    final TestJoinPoint joinPoint = new TestJoinPoint.Builder(this.getClass(), "toString")
        .withReturnType(this.getClass()).build();
    final TestProceedingJoinPoint proceedingJoinPoint = new TestProceedingJoinPoint(joinPoint);

    assertThat(LogObservable.methodAnnotatedWithRxLogObservable(proceedingJoinPoint)).isFalse();
  }
}