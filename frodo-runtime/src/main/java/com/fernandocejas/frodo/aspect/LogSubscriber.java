package com.fernandocejas.frodo.aspect;

import com.fernandocejas.frodo.internal.Counter;
import com.fernandocejas.frodo.internal.MessageManager;
import com.fernandocejas.frodo.internal.StopWatch;
import com.fernandocejas.frodo.joinpoint.FrodoJoinPoint;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import rx.Subscriber;

@Aspect
public class LogSubscriber {
  private static final String CLASS =
      "within(@com.fernandocejas.frodo.annotation.RxLogSubscriber *) && if()";

  private static final String METHOD_ON_START = "execution(void *.onStart())";
  private static final String METHOD_ON_NEXT = "execution(void *.onNext(..))";
  private static final String METHOD_ON_ERROR =
      "execution(void *.onError(java.lang.Throwable)) && args(throwable)";
  private static final String METHOD_ON_COMPLETED = "execution(void *.onCompleted())";

  private static final String METHOD_REQUEST = "call(void *.request(long)) && args(numberOfItems)";
  private static final String METHOD_UN_SUBSCRIBE = "call(void *.unsubscribe())";

  private final Counter counter;
  private final StopWatch stopWatch;
  private final MessageManager messageManager;
  private boolean isFirstElementEmitted = true;

  public LogSubscriber() {
    this(new Counter(), new StopWatch(), new MessageManager());
  }

  public LogSubscriber(Counter counter, StopWatch stopWatch, MessageManager messageManager) {
    this.counter = counter;
    this.stopWatch = stopWatch;
    this.messageManager = messageManager;
  }

  @Pointcut(CLASS)
  public static boolean classAnnotatedWithRxLogSubscriber(JoinPoint joinPoint) {
    return joinPoint.getTarget() instanceof Subscriber;
  }

  @Pointcut(METHOD_ON_START)
  public void onStartMethodExecution() {
  }

  @Pointcut(METHOD_ON_NEXT)
  public void onNextMethodExecution() {
  }

  @Pointcut(METHOD_ON_ERROR)
  public void onErrorMethodExecution(Throwable throwable) {
  }

  @Pointcut(METHOD_ON_COMPLETED)
  public void onCompletedMethodExecution() {
  }

  @Pointcut(METHOD_REQUEST)
  public void onRequestMethodCall(long numberOfItems) {
  }

  @Pointcut(METHOD_UN_SUBSCRIBE)
  public void onUnsubscribeMethodCall() {
  }

  @Before("classAnnotatedWithRxLogSubscriber(joinPoint) && onStartMethodExecution()")
  public void beforeOnStartExecution(JoinPoint joinPoint) {
    messageManager.printSubscriberOnStart(joinPoint.getTarget().getClass().getSimpleName());
  }

  @Before("classAnnotatedWithRxLogSubscriber(joinPoint) && onNextMethodExecution()")
  public void beforeOnNextExecution(JoinPoint joinPoint) {
    countAndMeasureTime();
    final FrodoJoinPoint frodoJoinPoint = new FrodoJoinPoint(joinPoint);
    final Object value = frodoJoinPoint.getMethodParamValuesList().isEmpty() ? null
        : frodoJoinPoint.getMethodParamValuesList().get(0);
    messageManager.printSubscriberOnNext(joinPoint.getTarget().getClass().getSimpleName(), value,
        Thread.currentThread().getName());
  }

  @After(value = "classAnnotatedWithRxLogSubscriber(joinPoint) && onErrorMethodExecution(throwable)",
      argNames = "joinPoint,throwable")
  public void afterOnErrorExecution(JoinPoint joinPoint, Throwable throwable) {
    stopWatch.stop();
    messageManager.printSubscriberOnError(joinPoint.getTarget().getClass().getSimpleName(),
        throwable.toString(),
        stopWatch.getTotalTimeMillis(), counter.tally());
    resetCounters();
  }

  @Before("classAnnotatedWithRxLogSubscriber(joinPoint) && onCompletedMethodExecution()")
  public void beforeOnCompletedExecution(JoinPoint joinPoint) {
    stopWatch.stop();
    messageManager.printSubscriberOnCompleted(joinPoint.getTarget().getClass().getSimpleName(),
        stopWatch.getTotalTimeMillis(), counter.tally());
    resetCounters();
  }

  @After("classAnnotatedWithRxLogSubscriber(joinPoint) && onUnsubscribeMethodCall()")
  public void afterUnsubscribeMethodCall(JoinPoint joinPoint) {
    messageManager.printSubscriberUnsubscribe(joinPoint.getTarget().getClass().getSimpleName());
  }

  @After("classAnnotatedWithRxLogSubscriber(joinPoint) && onRequestMethodCall(numberOfItems)")
  public void afterRequestMethodCall(JoinPoint joinPoint, long numberOfItems) {
    messageManager.printSubscriberRequestedItems(joinPoint.getTarget().getClass().getSimpleName(),
        numberOfItems);
  }

  private void countAndMeasureTime() {
    counter.increment();
    if (isFirstElementEmitted) {
      stopWatch.start();
    }
    isFirstElementEmitted = false;
  }

  private void resetCounters() {
    isFirstElementEmitted = true;
    counter.clear();
    stopWatch.reset();
  }
}
