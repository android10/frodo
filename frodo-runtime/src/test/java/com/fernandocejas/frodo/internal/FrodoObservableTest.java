package com.fernandocejas.frodo.internal;

import com.fernandocejas.frodo.joinpoint.FrodoProceedingJoinPoint;
import com.fernandocejas.frodo.joinpoint.TestJoinPoint;
import com.fernandocejas.frodo.joinpoint.TestProceedingJoinPoint;
import java.util.Collections;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import rx.Observable;
import rx.observers.TestSubscriber;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.verify;

@SuppressWarnings("unchecked")
@RunWith(MockitoJUnitRunner.class)
public class FrodoObservableTest {

  private static final String OBSERVABLE_STREAM_VALUE = "fernando";

  private FrodoObservable frodoObservable;

  private TestJoinPoint testJoinPoint;
  private TestProceedingJoinPoint testProceedingJoinPoint;
  private FrodoProceedingJoinPoint frodoProceedingJoinPoint;
  private TestSubscriber subscriber;

  @Mock private MessageManager messageManager;

  @Before
  public void setUp() {
    testJoinPoint = new TestJoinPoint.Builder(this.getClass())
        .withReturnType(Observable.class)
        .withReturnValue(OBSERVABLE_STREAM_VALUE)
        .build();
    testProceedingJoinPoint = new TestProceedingJoinPoint(testJoinPoint);
    frodoProceedingJoinPoint = new FrodoProceedingJoinPoint(testProceedingJoinPoint);
    frodoObservable = new FrodoObservable(frodoProceedingJoinPoint, messageManager);
    subscriber = new TestSubscriber();
  }

  @Test
  public void shouldPrintObservableInfo() throws Throwable {
    frodoObservable.getObservable();

    verify(messageManager).printObservableInfo(any(ObservableInfo.class));
  }

  @Test
  public void shouldBuildObservable() throws Throwable {
    frodoObservable.getObservable().subscribe(subscriber);

    subscriber.assertReceivedOnNext(Collections.singletonList(OBSERVABLE_STREAM_VALUE));
    subscriber.assertNoErrors();
    subscriber.assertCompleted();
    subscriber.assertUnsubscribed();
  }

  @Test
  public void shouldLogObservable() throws Throwable {
    frodoObservable.getObservable().subscribe(subscriber);

    verify(messageManager).printObservableOnSubscribe(any(ObservableInfo.class));
    verify(messageManager).printObservableOnNext(any(ObservableInfo.class), anyString());
    verify(messageManager).printObservableOnCompleted(any(ObservableInfo.class));
    verify(messageManager).printObservableOnTerminate(any(ObservableInfo.class));
    verify(messageManager).printObservableItemTimeInfo(any(ObservableInfo.class));
    verify(messageManager).printObservableOnUnsubscribe(any(ObservableInfo.class));
    verify(messageManager).printObservableThreadInfo(any(ObservableInfo.class));
  }
}