package com.fernandocejas.frodo.aspect;

import com.fernandocejas.frodo.internal.Counter;
import com.fernandocejas.frodo.internal.MessageManager;
import com.fernandocejas.frodo.internal.StopWatch;
import org.aspectj.lang.JoinPoint;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import rx.observers.TestSubscriber;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

@RunWith(MockitoJUnitRunner.class)
public class LogSubscriberTest {

  private LogSubscriber logSubscriber;

  @Mock private JoinPoint joinPoint;
  @Mock private Counter counter;
  @Mock private StopWatch stopWatch;
  @Mock private MessageManager messageManager;

  private TestSubscriber subscriber;

  @Before
  public void setUp() {
    logSubscriber = new LogSubscriber(counter, stopWatch, messageManager);
    subscriber = new TestSubscriber();

    given(joinPoint.getTarget()).willReturn(subscriber);
  }

  @Test
  public void annotatedClassMustBeSubscriber() {
    assertThat(LogSubscriber.classAnnotatedWithRxLogSubscriber(joinPoint)).isTrue();
    verify(joinPoint).getTarget();
    verifyNoMoreInteractions(joinPoint);
  }

  @Test
  public void printOnStartMessageBeforeSubscriberOnStartExecution() {
    logSubscriber.beforeOnStartExecution(joinPoint);

    verify(messageManager).printSubscriberOnStart(subscriber.getClass().getSimpleName());
  }
}