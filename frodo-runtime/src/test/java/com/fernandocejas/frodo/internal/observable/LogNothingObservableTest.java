package com.fernandocejas.frodo.internal.observable;

import com.fernandocejas.frodo.internal.MessageManager;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import rx.observers.TestSubscriber;

import static org.mockito.Mockito.verifyZeroInteractions;

@SuppressWarnings("unchecked") @RunWith(MockitoJUnitRunner.class)
public class LogNothingObservableTest {

  @Rule public ObservableRule observableRule = new ObservableRule(this.getClass());

  private LogNothingObservable loggableObservable;
  private TestSubscriber subscriber;

  @Mock private MessageManager messageManager;

  @Before
  public void setUp() {
    subscriber = new TestSubscriber();
    loggableObservable = new LogNothingObservable(observableRule.joinPoint(), messageManager,
        observableRule.info());
  }

  @Test
  public void shouldNotLogAnything() throws Throwable {
    loggableObservable.get(observableRule.stringType()).subscribe(subscriber);

    subscriber.assertNoErrors();
    subscriber.assertCompleted();
    verifyZeroInteractions(messageManager);
  }
}