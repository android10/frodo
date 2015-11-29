package com.fernandocejas.frodo.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.CLASS;

/**
 * <br>Annotated classes which are of type rx.Subscriber will print the following information on
 * android logcat when receiving items from an rx.Observable.
 * A {@link Scope} option can be passed to choose different logging scopes.<br>
 *
 * <br>OUTPUT EXAMPLE:<br>
 *
 * <br>Frodo => [@Subscriber :: MySubscriberBackpressure -> onStart()]
 * <br>Frodo => [@Subscriber :: MySubscriberBackpressure -> @Requested -> 10 elements]
 * <br>Frodo => [@Subscriber :: MySubscriberBackpressure -> onNext() -> 1 :: @ObserveOn -> main]
 * <br>Frodo => [@Subscriber :: MySubscriberBackpressure -> onNext() -> 2 :: @ObserveOn -> main]
 * <br>Frodo => [@Subscriber :: MySubscriberBackpressure -> onNext() -> 3 :: @ObserveOn -> main]
 * <br>Frodo => [@Subscriber :: MySubscriberBackpressure -> onCompleted()]
 * <br>Frodo => [@Subscriber :: MySubscriberBackpressure -> @Received -> 16 elements :: @Time -> 6 ms]
 * <br>Frodo => [@Subscriber :: MySubscriberBackpressure -> unSubscribe()]<br>
 *
 * @see <a href="https://github.com/android10/frodo/wiki">Frodo Documentation</a>
 */
@Retention(CLASS)
@Target({ TYPE })
public @interface RxLogSubscriber {
  Scope value() default Scope.EVERYTHING;

  /**
   * Logging scope of the current annotated rx.Subscriber.<br>
   *
   * <li>{@link #EVERYTHING}: Logs stream data, scheduler and rx.Subscriber events. Default.</li>
   * <li>{@link #STREAM}: Logs rx.Subscribers received items plus total execution time.</li>
   * <li>{@link #SCHEDULER}: Logs scheduler where the annotated rx.Subscriber operates on.</li>
   * <li>{@link #EVENTS}: Logs rx.Subscriber events only.</li>
   * <li>{@link #NOTHING}: Turns off logging for the annotated rx.Subscriber.</li>
   */
  enum Scope { EVERYTHING, STREAM, SCHEDULER, EVENTS, NOTHING }
}
