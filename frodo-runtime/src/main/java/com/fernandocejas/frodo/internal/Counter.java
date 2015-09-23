package com.fernandocejas.frodo.internal;

import java.util.concurrent.atomic.AtomicInteger;

public class Counter implements Comparable<Counter> {

  private final String name;
  private final AtomicInteger count;

  public Counter() {
    this(Counter.class.getSimpleName());
  }

  public Counter(String name) {
    this.name = name;
    this.count = new AtomicInteger(0);
  }

  public void increment() {
    count.incrementAndGet();
  }

  public void decrement() {
    count.decrementAndGet();
  }

  public int tally() {
    return count.intValue();
  }

  public void clear() {
    count.set(0);
  }

  @Override
  public int compareTo(Counter that) {
    final int thisValue = count.intValue();
    final int thatValue = that.count.intValue();

    if (thisValue < thatValue) { return -1; } else if (thisValue > thatValue) { return +1; } else {
      return 0;
    }
  }

  public String toString() {
    return getClass().getSimpleName() + " name: " + name + " value: " + count.intValue();
  }
}
