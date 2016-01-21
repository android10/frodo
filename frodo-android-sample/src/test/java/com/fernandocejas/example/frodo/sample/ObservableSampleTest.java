package com.fernandocejas.example.frodo.sample;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import rx.Observable;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(RobolectricTestRunner.class)
public class ObservableSampleTest {

  @Test
  public void returnsObservableInteger() throws Exception {
    ObservableSample objectUnderTest = new ObservableSample();
    Observable<Integer> numbers = objectUnderTest.numbers();
    assertThat(numbers.toList().toBlocking().single()).contains(1, 2);
  }
}