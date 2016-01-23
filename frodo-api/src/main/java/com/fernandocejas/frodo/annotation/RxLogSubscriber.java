package com.fernandocejas.frodo.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * <br>Annotated classes which are of type rx.Subscriber will print the following information on
 * android logcat when receiving items from an rx.Observable.<br>
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
@Retention(RUNTIME)
@Target({ TYPE })
public @interface RxLogSubscriber {
}
