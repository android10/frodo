package com.fernandocejas.frodo.aspect;

import com.fernandocejas.frodo.JavaUnitTest;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import rx.Observable;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

public class LogObservableTest extends JavaUnitTest {

  @Mock private ProceedingJoinPoint proceedingJoinPoint;
  @Mock private MethodSignature signature;

  @Before
  public void setUp() {
    given(proceedingJoinPoint.getSignature()).willReturn(signature);
    given(signature.getReturnType()).willReturn(Observable.class);
  }

  @Test
  public void annotatedMethodMustReturnObservable() {
    assertThat(LogObservable.methodAnnotatedWithRxLogObservable(proceedingJoinPoint)).isTrue();
    verify(signature).getReturnType();
    verifyNoMoreInteractions(signature);
  }
}