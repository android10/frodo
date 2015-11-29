/*
 * Copyright (C) 2007 The Guava Authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.fernandocejas.frodo.core.functions;

import com.fernandocejas.frodo.core.objects.MoreObjects;
import java.io.Serializable;
import org.jetbrains.annotations.Nullable;

import static com.fernandocejas.frodo.core.checks.Preconditions.checkNotNull;

/**
 * Static utility methods pertaining to {@code Function} instances.
 * <p/>
 * <p>All methods return serializable functions as long as they're given serializable parameters.
 * <p/>
 * <p>See the Guava User Guide article on <a href=
 * "http://code.google.com/p/guava-libraries/wiki/FunctionalExplained">the use of {@code
 * Function}</a>.
 *
 * @author Mike Bostock
 * @author Jared Levy
 * @since 2.0 (imported from Google Collections Library)
 *
 * <p><b>This class contains code derived from <a href="https://github.com/google/guava">Google
 * Guava</a></b>
 */
public final class Functions {

  /**
   * Returns a function that calls {@code toString()} on its argument. The function does not accept
   * nulls; it will throw a {@link NullPointerException} when applied to {@code null}.
   * <p/>
   * <p><b>Warning:</b> The returned function may not be <i>consistent with equals</i> (as
   * documented at {@link Function#apply}). For example, this function yields different results for
   * the two equal instances {@code ImmutableSet.of(1, 2)} and {@code ImmutableSet.of(2, 1)}.
   */
  public static Function<Object, String> toStringFunction() {
    return ToStringFunction.INSTANCE;
  }

  // enum singleton pattern
  private enum ToStringFunction implements Function<Object, String> {
    INSTANCE;

    @Override
    public String apply(Object o) {
      if (o != null) {
        return o.toString();
      } else {
        throw new NullPointerException();
      }
    }

    @Override
    public String toString() {
      return "toString";
    }
  }

  /**
   * Returns the identity function.
   */
  // implementation is "fully variant"; E has become a "pass-through" type
  @SuppressWarnings("unchecked")
  public static <E> Function<E, E> identity() {
    return (Function<E, E>) IdentityFunction.INSTANCE;
  }

  private enum IdentityFunction implements Function<Object, Object> {
    INSTANCE;

    @Override
    public Object apply(Object o) {
      checkNotNull(o);
      return o;
    }

    @Override
    public String toString() {
      return "identity";
    }
  }

  /**
   * Creates a function that returns {@code value} for any input.
   *
   * @param value the constant value for the function to return
   * @return a function that always returns {@code value}
   */
  public static <E> Function<Object, E> constant(@Nullable E value) {
    return new ConstantFunction<>(value);
  }

  private static class ConstantFunction<E> implements Function<Object, E>, Serializable {
    private static final long serialVersionUID = 0;

    private final E value;

    public ConstantFunction(@Nullable E value) {
      this.value = value;
    }

    @Override
    public E apply(Object from) {
      checkNotNull(from);
      return value;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
      if (obj instanceof ConstantFunction) {
        ConstantFunction<?> that = (ConstantFunction<?>) obj;
        return MoreObjects.equal(value, that.value);
      }
      return false;
    }

    @Override
    public int hashCode() {
      return (value == null) ? 0 : value.hashCode();
    }

    @Override
    public String toString() {
      return "constant(" + value + ")";
    }
  }

  private Functions() {
    // no instances
  }
}
