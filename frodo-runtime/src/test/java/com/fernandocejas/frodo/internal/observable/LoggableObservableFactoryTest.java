package com.fernandocejas.frodo.internal.observable;

import com.fernandocejas.frodo.annotation.RxLogObservable;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

@RunWith(MockitoJUnitRunner.class)
public class LoggableObservableFactoryTest {

  @Rule public final ObservableRule observableRule = new ObservableRule(this.getClass());

  private LoggableObservableFactory observableFactory;

  @Before
  public void setUp() {
    observableFactory = new LoggableObservableFactory(observableRule.joinPoint(),
        observableRule.messageManager(), observableRule.info());
  }

  @Test
  public void shouldCreateLogEverythingObservable() {
    final RxLogObservable annotation = mock(RxLogObservable.class);
    given(annotation.value()).willReturn(RxLogObservable.Scope.EVERYTHING);

    final LoggableObservable loggableObservable = observableFactory.create(annotation);

    assertThat(loggableObservable).isInstanceOf(LogEverythingObservable.class);
  }

  @Test
  public void shouldCreateLogStreamObservable() {
    final RxLogObservable annotation = mock(RxLogObservable.class);
    given(annotation.value()).willReturn(RxLogObservable.Scope.STREAM);

    final LoggableObservable loggableObservable = observableFactory.create(annotation);

    assertThat(loggableObservable).isInstanceOf(LogStreamObservable.class);
  }

  @Test
  public void shouldCreateLogEventsObservable() {
    final RxLogObservable annotation = mock(RxLogObservable.class);
    given(annotation.value()).willReturn(RxLogObservable.Scope.EVENTS);

    final LoggableObservable loggableObservable = observableFactory.create(annotation);

    assertThat(loggableObservable).isInstanceOf(LogEventsObservable.class);
  }

  @Test
  public void shouldCreateLogSchedulersObservable() {
    final RxLogObservable annotation = mock(RxLogObservable.class);
    given(annotation.value()).willReturn(RxLogObservable.Scope.SCHEDULERS);

    final LoggableObservable loggableObservable = observableFactory.create(annotation);

    assertThat(loggableObservable).isInstanceOf(LogSchedulersObservable.class);
  }

  @Test
  public void shouldCreateLogNothingObservable() {
    final RxLogObservable annotation = mock(RxLogObservable.class);
    given(annotation.value()).willReturn(RxLogObservable.Scope.NOTHING);

    final LoggableObservable loggableObservable = observableFactory.create(annotation);

    assertThat(loggableObservable).isInstanceOf(LogNothingObservable.class);
  }
}