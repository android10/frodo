package com.fernandocejas.frodo.internal;

import com.fernandocejas.frodo.joinpoint.FrodoProceedingJoinPoint;
import com.fernandocejas.frodo.joinpoint.TestJoinPoint;
import com.fernandocejas.frodo.joinpoint.TestProceedingJoinPoint;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class FrodoObservableTest {

  private FrodoObservable frodoObservable;

  private TestJoinPoint testJoinPoint;
  private TestProceedingJoinPoint testProceedingJoinPoint;
  private FrodoProceedingJoinPoint frodoProceedingJoinPoint;

  @Mock private MessageManager messageManager;

  @Before
  public void setUp() {
    testJoinPoint = new TestJoinPoint.Builder(this.getClass()).build();
    testProceedingJoinPoint = new TestProceedingJoinPoint(testJoinPoint);
    frodoProceedingJoinPoint = new FrodoProceedingJoinPoint(testProceedingJoinPoint);
  }
}