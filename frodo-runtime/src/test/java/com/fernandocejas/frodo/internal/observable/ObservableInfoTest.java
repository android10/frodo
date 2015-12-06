package com.fernandocejas.frodo.internal.observable;

import com.fernandocejas.frodo.core.optional.Optional;
import com.fernandocejas.frodo.internal.observable.ObservableInfo;
import com.fernandocejas.frodo.joinpoint.FrodoJoinPoint;
import com.fernandocejas.frodo.joinpoint.TestJoinPoint;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;
import rx.Observable;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class ObservableInfoTest {

  private static final String OBSERVABLE_STREAM_VALUE = "fernando";

  private ObservableInfo observableInfo;

  @Before
  public void setUp() {
    final TestJoinPoint testJoinPoint = new TestJoinPoint.Builder(this.getClass())
        .withReturnType(Observable.class)
        .withReturnValue(OBSERVABLE_STREAM_VALUE)
        .build();
    final FrodoJoinPoint frodoJoinPoint = new FrodoJoinPoint(testJoinPoint);
    observableInfo = new ObservableInfo(frodoJoinPoint);
  }

  @Test
  public void shouldReturnAbsentValues() {
    final Optional<String> optionalSubscribeOnThread = observableInfo.getSubscribeOnThread();
    final Optional<String> optionalObserveOnThread = observableInfo.getObserveOnThread();
    final Optional<Long> optionalTotalExecutionTime = observableInfo.getTotalExecutionTime();
    final Optional<Integer> optionalTotalEmittedItems = observableInfo.getTotalEmittedItems();

    assertThat(optionalSubscribeOnThread.isPresent()).isFalse();
    assertThat(optionalObserveOnThread.isPresent()).isFalse();
    assertThat(optionalTotalExecutionTime.isPresent()).isFalse();
    assertThat(optionalTotalEmittedItems.isPresent()).isFalse();
  }

  @Test
  public void shouldReturnPresentValues() {
    observableInfo.setSubscribeOnThread("thread");
    observableInfo.setObserveOnThread("thread");
    observableInfo.setTotalExecutionTime(1000);
    observableInfo.setTotalEmittedItems(5);

    final Optional<String> optionalSubscribeOnThread = observableInfo.getSubscribeOnThread();
    final Optional<String> optionalObserveOnThread = observableInfo.getObserveOnThread();
    final Optional<Long> optionalTotalExecutionTime = observableInfo.getTotalExecutionTime();
    final Optional<Integer> optionalTotalEmittedItems = observableInfo.getTotalEmittedItems();

    assertThat(optionalSubscribeOnThread.isPresent()).isTrue();
    assertThat(optionalObserveOnThread.isPresent()).isTrue();
    assertThat(optionalTotalExecutionTime.isPresent()).isTrue();
    assertThat(optionalTotalEmittedItems.isPresent()).isTrue();
  }
}