package com.fernandocejas.frodo.internal.observable;

import com.fernandocejas.frodo.core.optional.Optional;
import com.fernandocejas.frodo.internal.MessageManager;
import java.util.concurrent.TimeUnit;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import rx.observers.TestSubscriber;
import rx.schedulers.TestScheduler;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

@SuppressWarnings("unchecked") @RunWith(MockitoJUnitRunner.class)
public class LogStreamObservableTest {

  @Rule public ObservableRule observableRule = new ObservableRule(this.getClass());

  private LogStreamObservable loggableObservable;
  private TestSubscriber subscriber;

  @Mock private MessageManager messageManager;

  @Before
  public void setUp() {
    subscriber = new TestSubscriber();
    loggableObservable =
        new LogStreamObservable(observableRule.joinPoint(), messageManager, observableRule.info());
  }

  @Test
  public void shouldLogOnlyStreamData() throws Throwable {
    loggableObservable.get(observableRule.stringType()).subscribe(subscriber);

    verify(messageManager).printObservableOnNextWithValue(any(ObservableInfo.class), anyString());
    verify(messageManager).printObservableItemTimeInfo(any(ObservableInfo.class));
    verifyNoMoreInteractions(messageManager);
  }

  @Test
  public void shouldFillInObservableItemsInfo() throws Throwable {
    final TestScheduler testScheduler = new TestScheduler();
    loggableObservable.get(observableRule.stringType())
        .delay(2, TimeUnit.SECONDS)
        .subscribeOn(testScheduler)
        .subscribe(subscriber);

    testScheduler.advanceTimeBy(1, TimeUnit.SECONDS);
    testScheduler.advanceTimeBy(2, TimeUnit.SECONDS);

    final ObservableInfo observableInfo = loggableObservable.getInfo();
    final Optional<Integer> totalEmittedItems = observableInfo.getTotalEmittedItems();
    final Optional<Long> totalExecutionTime = observableInfo.getTotalExecutionTime();

    assertThat(totalEmittedItems.isPresent()).isTrue();
    assertThat(totalEmittedItems.get()).isEqualTo(1);
  }
}