package com.fernandocejas.frodo.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.CLASS;

/**
 * <br>Annotated methods which return rx.Observables will print the following information on
 * android logcat when emitting items.<br>
 *
 * <br>OUTPUT EXAMPLE:<br>
 *
 * <br>Frodo => [@Observable :: @InClass -> ObservableSample :: @Method -> names()]
 * <br>Frodo => [@Observable#names -> onSubscribe() :: @SubscribeOn -> RxNewThreadScheduler-8]
 * <br>Frodo => [@Observable#names -> onNext() -> Fernando]
 * <br>Frodo => [@Observable#names -> onNext() -> Silvia]
 * <br>Frodo => [@Observable#names -> onCompleted()]
 * <br>Frodo => [@Observable#names -> onTerminate() :: @Emitted -> 2 elements :: @Time -> 1 ms]
 * <br>Frodo => [@Observable#names -> onUnsubscribe()]<br>
 *
 * <br>------------------------------------------------------------------------------------------
 * <br>"I am glad you are here with me. Here at the end of all things, Sam." -Frodo
 * <br>------------------------------------------------------------------------------------------
 */
@Retention(CLASS)
@Target({ METHOD })
public @interface RxLogObservable {
}
