package com.fernandocejas.frodo.aspect;

import com.fernandocejas.frodo.internal.Counter;
import com.fernandocejas.frodo.internal.MessageManager;
import com.fernandocejas.frodo.internal.StopWatch;
import com.fernandocejas.frodo.joinpoint.TestJoinPoint;
import com.fernandocejas.frodo.joinpoint.TestProceedingJoinPoint;
import org.aspectj.lang.JoinPoint;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import rx.observers.TestSubscriber;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

@RunWith(MockitoJUnitRunner.class)
public class LogSubscriberTest {

  private LogSubscriber logSubscriber;

  @Mock private Counter counter;
  @Mock private StopWatch stopWatch;
  @Mock private MessageManager messageManager;

  private TestSubscriber subscriber;
  private TestJoinPoint joinPoint;

  @Before
  public void setUp() {
    logSubscriber = new LogSubscriber(counter, stopWatch, messageManager);
    subscriber = new TestSubscriber();
    joinPoint = new TestJoinPoint.Builder(subscriber.getClass())
        .withParamTypes(String.class)
        .withParamNames("param")
        .withParamValues("value")
        .build();
  }

  @Test
  public void annotatedClassMustCheckTargetType() {
    final JoinPoint joinPoint = mock(JoinPoint.class);
    given(joinPoint.getTarget()).willReturn(subscriber);

    assertThat(LogSubscriber.classAnnotatedWithRxLogSubscriber(joinPoint)).isTrue();
    verify(joinPoint).getTarget();
    verifyNoMoreInteractions(joinPoint);
  }

  @Test
  public void shouldWeaveClassOfTypeSubscriber() {
    final TestJoinPoint joinPoint = new TestJoinPoint.Builder(subscriber.getClass()).build();
    final TestProceedingJoinPoint proceedingJoinPoint = new TestProceedingJoinPoint(joinPoint);

    assertThat(LogSubscriber.classAnnotatedWithRxLogSubscriber(proceedingJoinPoint)).isTrue();
  }

  @Test
  public void shouldNotWeaveClassOfOtherTypeThanSubscriber() {
    final TestJoinPoint joinPoint = new TestJoinPoint.Builder(this.getClass()).build();
    final TestProceedingJoinPoint proceedingJoinPoint = new TestProceedingJoinPoint(joinPoint);

    assertThat(LogSubscriber.classAnnotatedWithRxLogSubscriber(proceedingJoinPoint)).isFalse();
  }

  @Test
  public void printOnStartMessageBeforeSubscriberOnStartExecution() {
    logSubscriber.beforeOnStartExecution(joinPoint);

    verify(messageManager).printSubscriberOnStart(subscriber.getClass().getSimpleName());
  }

  @Test
  public void printOnNextMessageBeforeSubscriberOnNextExecution() {
    logSubscriber.beforeOnNextExecution(joinPoint);

    verify(counter).increment();
    verify(stopWatch).start();
    verify(messageManager).printSubscriberOnNext(eq(subscriber.getClass().getSimpleName()),
        eq("value"), anyString());
  }

  @Test public void printOnNextMessageBeforeSubscriberOnNextExecutionWithEmptyValues() {
    final TestJoinPoint joinPointTest =
        new TestJoinPoint.Builder(subscriber.getClass()).withParamTypes(String.class)
            .withParamNames("param")
            .withParamValues()
            .build();
    logSubscriber.beforeOnNextExecution(joinPointTest);

    verify(counter).increment();
    verify(stopWatch).start();
    verify(messageManager).printSubscriberOnNext(eq(subscriber.getClass().getSimpleName()),
        anyObject(), anyString());
  }

  @Test
  public void printOnErrorMessageAfterSubscriberOnErrorExecution() {
    logSubscriber.afterOnErrorExecution(joinPoint, new IllegalStateException());

    verify(stopWatch).stop();
    verify(counter).tally();
    verify(messageManager).printSubscriberOnError(eq(subscriber.getClass().getSimpleName()),
        anyString(), anyLong(), anyInt());
    verify(counter).clear();
    verify(stopWatch).reset();
  }

  @Test
  public void printOnCompleteMessageBeforeSubscriberOnCompleteExecution() {
    logSubscriber.beforeOnCompletedExecution(joinPoint);

    verify(stopWatch).stop();
    verify(messageManager).printSubscriberOnCompleted(eq(subscriber.getClass().getSimpleName()),
        anyLong(), anyInt());
    verify(counter).tally();
    verify(counter).clear();
    verify(stopWatch).getTotalTimeMillis();
    verify(stopWatch).reset();
  }

  @Test
  public void printUnsubscribeMessageAfterSubscriberUnsubscribeMethodCall() {
    logSubscriber.afterUnsubscribeMethodCall(joinPoint);

    verify(messageManager).printSubscriberUnsubscribe(subscriber.getClass().getSimpleName());
  }

  @Test
  public void printRequestedItemsAfterSubscriberRequestMethodCall() {
    logSubscriber.afterRequestMethodCall(joinPoint, 10);

    verify(messageManager).printSubscriberRequestedItems(subscriber.getClass().getSimpleName(), 10);
  }
}