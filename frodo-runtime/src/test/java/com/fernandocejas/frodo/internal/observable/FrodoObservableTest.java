package com.fernandocejas.frodo.internal.observable;

import com.fernandocejas.frodo.internal.MessageManager;
import java.lang.annotation.Annotation;
import java.util.Collections;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import rx.observers.TestSubscriber;

import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;

@SuppressWarnings("unchecked")
@RunWith(MockitoJUnitRunner.class)
public class FrodoObservableTest {

  @Rule public ObservableRule observableRule = new ObservableRule(this.getClass());

  private FrodoObservable frodoObservable;
  private TestSubscriber subscriber;

  @Mock private MessageManager messageManager;
  @Mock private LoggableObservableFactory observableFactory;

  @Before
  public void setUp() {
    frodoObservable =
        new FrodoObservable(observableRule.joinPoint(), messageManager, observableFactory);
    subscriber = new TestSubscriber();

    given(observableFactory.create(any(Annotation.class))).willReturn(
        createLogEverythingObservable());
  }

  @Test
  public void shouldPrintObservableInfo() throws Throwable {
    frodoObservable.getObservable();

    verify(messageManager).printObservableInfo(any(ObservableInfo.class));
  }

  @Test
  public void shouldBuildObservable() throws Throwable {
    frodoObservable.getObservable().subscribe(subscriber);

    subscriber.assertReceivedOnNext(
        Collections.singletonList(observableRule.OBSERVABLE_STREAM_VALUE));
    subscriber.assertNoErrors();
    subscriber.assertCompleted();
    subscriber.assertUnsubscribed();
  }

  @Test
  public void shouldLogObservableInformation() throws Throwable {
    frodoObservable.getObservable().subscribe(subscriber);

    verify(messageManager).printObservableInfo(any(ObservableInfo.class));
  }

  private LogEverythingObservable createLogEverythingObservable() {
    return new LogEverythingObservable(observableRule.joinPoint(), messageManager,
        new ObservableInfo(observableRule.joinPoint()));
  }
}