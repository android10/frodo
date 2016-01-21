package com.fernandocejas.frodo.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * <br>Annotated methods which return rx.Observables will print the following information on
 * android logcat when emitting items.
 * A {@link Scope} option can be passed to choose different logging scopes.<br>
 *
 * <br>OUTPUT EXAMPLE:<br>
 *
 * <br>Frodo => [@Observable :: @InClass -> ObservableSample :: @Method -> names()]
 * <br>Frodo => [@Observable#names -> onSubscribe()]
 * <br>Frodo => [@Observable#names -> onNext() -> Fernando]
 * <br>Frodo => [@Observable#names -> onNext() -> Silvia]
 * <br>Frodo => [@Observable#names -> onCompleted()]
 * <br>Frodo => [@Observable#names -> onTerminate()]
 * <br>Frodo => [@Observable#names -> @Emitted -> 2 elements :: @Time -> 1 ms]
 * <br>Frodo => [@Observable#names -> @SubscribeOn -> RxNewThreadScheduler-8 :: @ObserveOn -> main]
 * <br>Frodo => [@Observable#names -> onUnsubscribe()]<br>
 *
 * @see <a href="https://github.com/android10/frodo/wiki">Frodo Documentation</a>
 */
@Retention(RUNTIME)
@Target({ METHOD })
public @interface RxLogObservable {
  Scope value() default Scope.EVERYTHING;

  /**
   * Logging scope of the current annotated rx.Observable.<br>
   *
   * <li>{@link #EVERYTHING}: Logs stream data, schedulers and rx.Observable events. Default.</li>
   * <li>{@link #STREAM}: Logs rx.Observable emitted items plus total execution time.</li>
   * <li>{@link #SCHEDULERS}: Logs schedulers where the annotated rx.Observable operates on.</li>
   * <li>{@link #EVENTS}: Logs rx.Observable events only.</li>
   * <li>{@link #NOTHING}: Turns off logging for the annotated rx.Observable.</li>
   */
  enum Scope { EVERYTHING, STREAM, SCHEDULERS, EVENTS, NOTHING }
}
