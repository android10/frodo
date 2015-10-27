package com.fernandocejas.frodo.internal;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;
import rx.Observable;
import rx.observers.TestSubscriber;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class StopWatchTest {

  private StopWatch stopWatch;

  @Before
  public void setUp() {
    stopWatch = new StopWatch();
  }

  @Test
  public void mustResetStopWatch() {
    stopWatch.reset();

    assertThat(stopWatch.getTotalTimeMillis()).isZero();
  }

  @Test
  public void mustStartStopWatch() throws InterruptedException {
    stopWatch.start();
    Thread.sleep(10);
    stopWatch.stop();

    assertThat(stopWatch.getTotalTimeMillis()).isGreaterThan(0L);
  }
}