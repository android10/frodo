package com.fernandocejas.frodo.joinpoint;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;
import rx.Observable;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class FrodoJoinPointTest {

  @Test
  public void shouldReturnCorrectParametersList() {
    final Class[] paramTypes = { String.class, String.class };
    final String[] paramNames = { "paramOne", "paramTwo" };
    final Object[] paramValues = { "ValueOne", "ValueTwo" };

    final TestJoinPoint testJoinPoint = new TestJoinPoint.Builder(this.getClass())
        .withParamTypes(paramTypes)
        .withParamNames(paramNames)
        .withParamValues(paramValues)
        .build();
    final FrodoJoinPoint frodoJoinPoint = new FrodoJoinPoint(testJoinPoint);

    final List<Class> paramTypeList = frodoJoinPoint.getMethodParamTypesList();
    final List<String> paramNamesList = frodoJoinPoint.getMethodParamNamesList();
    final List<Object> paramValuesList = frodoJoinPoint.getMethodParamValuesList();

    assertThat(paramTypeList).isEqualTo(Arrays.asList(paramTypes));
    assertThat(paramNamesList).isEqualTo(Arrays.asList(paramNames));
    assertThat(paramValuesList).isEqualTo(Arrays.asList(paramValues));
  }

  @Test
  public void shouldHaveReturnType() {
    final TestJoinPoint testJoinPoint = new TestJoinPoint.Builder(this.getClass())
        .withReturnType(Observable.class)
        .build();
    final FrodoJoinPoint frodoJoinPoint = new FrodoJoinPoint(testJoinPoint);

    assertThat(frodoJoinPoint.hasReturnType(testJoinPoint)).isTrue();
    assertThat(frodoJoinPoint.getReturnType()).isEqualTo(Observable.class);
  }

  @Test
  public void shouldNotHaveReturnType() {
    final TestJoinPoint testJoinPoint = new TestJoinPoint.Builder(this.getClass())
        .withReturnType(void.class)
        .build();
    final FrodoJoinPoint frodoJoinPoint = new FrodoJoinPoint(testJoinPoint);

    assertThat(frodoJoinPoint.hasReturnType(testJoinPoint)).isFalse();
    assertThat(frodoJoinPoint.getReturnType()).isEqualTo(void.class);
  }

  @Test
  public void shouldReturnCorrectGenericReturnType() {
    final TestJoinPoint testJoinPoint = new TestJoinPoint.Builder(MyDummyClass.class, "buildDummyObservable")
        .withReturnType(Observable.class)
        .build();
    final FrodoJoinPoint frodoJoinPoint = new FrodoJoinPoint(testJoinPoint);

    assertThat(frodoJoinPoint.getGenericReturnTypes()).isEqualTo(
        Collections.singletonList(Observable.class));
  }

  @Test
  public void shouldReturnCorrectGenericParameterizedReturnType() {
    final TestJoinPoint testJoinPoint = new TestJoinPoint.Builder(MyDummyClass.class, "toString")
        .withReturnType(Observable.class)
        .build();
    final FrodoJoinPoint frodoJoinPoint = new FrodoJoinPoint(testJoinPoint);

    assertThat(frodoJoinPoint.getGenericReturnTypes()).isEqualTo(
        Collections.singletonList(String.class));
  }

  @Test
  public void mustGenerateJoinPointUniqueNameForEqualityComparison() {
    final Class[] paramTypes = { String.class, String.class };
    final String[] paramNames = { "paramOne", "paramTwo" };
    final Object[] paramValues = { "ValueOne", "ValueTwo" };

    final TestJoinPoint testJoinPointOne = new TestJoinPoint.Builder(this.getClass())
        .withParamTypes(paramTypes)
        .withParamNames(paramNames)
        .withParamValues(paramValues)
        .build();
    final TestJoinPoint testJoinPointTwo = new TestJoinPoint.Builder(this.getClass()).build();

    final FrodoJoinPoint frodoJoinPointOne = new FrodoJoinPoint(testJoinPointOne);
    final FrodoJoinPoint frodoJoinPointTwo = new FrodoJoinPoint(testJoinPointTwo);

    assertThat(frodoJoinPointOne).isNotEqualTo(frodoJoinPointTwo);
    assertThat(frodoJoinPointOne.hashCode()).isNotEqualTo(frodoJoinPointTwo.hashCode());
  }

  private static class MyDummyClass {
    public MyDummyClass() {
    }

    public Observable buildDummyObservable() {
      return Observable.empty();
    }

    @Override public String toString() {
      return this.getClass().getSimpleName();
    }
  }
}