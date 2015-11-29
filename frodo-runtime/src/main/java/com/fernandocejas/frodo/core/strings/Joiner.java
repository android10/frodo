/*
 * Copyright (C) 2008 The Guava Authors
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

package com.fernandocejas.frodo.core.strings;

import java.io.IOException;
import java.util.AbstractList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Map;
import org.jetbrains.annotations.Nullable;

import static com.fernandocejas.frodo.core.checks.Preconditions.checkNotNull;

/**
 * An object which joins pieces of text (specified as an array, {@link Iterable}, varargs or even a
 * {@link Map}) with a separator. It either appends the results to an {@link Appendable} or returns
 * them as a {@link String}. Example: <pre>   {@code
 * <p/>
 *   Joiner joiner = Joiner.on("; ").skipNulls();
 *    . . .
 *   return joiner.join("Harry", null, "Ron", "Hermione");}</pre>
 * <p/>
 * <p>This returns the string {@code "Harry; Ron; Hermione"}. Note that all input elements are
 * converted to strings using {@link Object#toString()} before being appended.
 * <p/>
 * <p>If neither {@link #skipNulls()} nor {@link #useForNull(String)} is specified, the joining
 * methods will throw {@link NullPointerException} if any given element is null.
 * <p/>
 * <p><b>Warning: joiner instances are always immutable</b>; a configuration method such as {@code
 * useForNull} has no effect on the instance it is invoked on! You must store and use the new
 * joiner
 * instance returned by the method. This makes joiners thread-safe, and safe to store as {@code
 * static final} constants. <pre>   {@code
 * <p/>
 *   // Bad! Do not do this!
 *   Joiner joiner = Joiner.on(',');
 *   joiner.skipNulls(); // does nothing!
 *   return joiner.join("wrong", null, "wrong");}</pre>
 * <p/>
 * <p>See the Guava User Guide article on <a href=
 * "http://code.google.com/p/guava-libraries/wiki/StringsExplained#Joiner">{@code Joiner}</a>.
 *
 * <p><b>This class contains code derived from <a href="https://github.com/google/guava">Google
 * Guava</a></b>
 *
 * @author Kevin Bourrillion
 */
public class Joiner {

  private final String separator;

  Joiner(String separator) {
    this.separator = checkNotNull(separator);
  }

  private Joiner(Joiner prototype) {
    this.separator = prototype.separator;
  }

  /**
   * Appends the string representation of each of {@code parts}, using the previously configured
   * separator between each, to {@code appendable}.
   */
  public <A extends Appendable> A appendTo(A appendable, Iterable<?> parts) throws IOException {
    return appendTo(appendable, parts.iterator());
  }

  /**
   * Appends the string representation of each of {@code parts}, using the previously configured
   * separator between each, to {@code appendable}.
   *
   * @since 11.0
   */
  public <A extends Appendable> A appendTo(A appendable, Iterator<?> parts) throws IOException {
    checkNotNull(appendable);
    if (parts.hasNext()) {
      appendable.append(toString(parts.next()));
      while (parts.hasNext()) {
        appendable.append(separator);
        appendable.append(toString(parts.next()));
      }
    }
    return appendable;
  }

  /**
   * Appends the string representation of each of {@code parts}, using the previously configured
   * separator between each, to {@code appendable}.
   */
  public final <A extends Appendable> A appendTo(A appendable, Object[] parts) throws IOException {
    return appendTo(appendable, Arrays.asList(parts));
  }

  /**
   * Appends to {@code appendable} the string representation of each of the remaining arguments.
   */
  public final <A extends Appendable> A appendTo(
      A appendable, @Nullable Object first, @Nullable Object second, Object... rest)
      throws IOException {
    return appendTo(appendable, iterable(first, second, rest));
  }

  /**
   * Appends the string representation of each of {@code parts}, using the previously configured
   * separator between each, to {@code builder}. Identical to {@link #appendTo(Appendable,
   * Iterable)}, except that it does not throw {@link IOException}.
   */
  public final StringBuilder appendTo(StringBuilder builder, Iterable<?> parts) {
    return appendTo(builder, parts.iterator());
  }

  /**
   * Appends the string representation of each of {@code parts}, using the previously configured
   * separator between each, to {@code builder}. Identical to {@link #appendTo(Appendable,
   * Iterable)}, except that it does not throw {@link IOException}.
   *
   * @since 11.0
   */
  public final StringBuilder appendTo(StringBuilder builder, Iterator<?> parts) {
    try {
      appendTo((Appendable) builder, parts);
    } catch (IOException impossible) {
      throw new AssertionError(impossible);
    }
    return builder;
  }

  /**
   * Appends the string representation of each of {@code parts}, using the previously configured
   * separator between each, to {@code builder}. Identical to {@link #appendTo(Appendable,
   * Iterable)}, except that it does not throw {@link IOException}.
   */
  public final StringBuilder appendTo(StringBuilder builder, Object[] parts) {
    return appendTo(builder, Arrays.asList(parts));
  }

  /**
   * Appends to {@code builder} the string representation of each of the remaining arguments.
   * Identical to {@link #appendTo(Appendable, Object, Object, Object...)}, except that it does not
   * throw {@link IOException}.
   */
  public final StringBuilder appendTo(
      StringBuilder builder, @Nullable Object first, @Nullable Object second, Object... rest) {
    return appendTo(builder, iterable(first, second, rest));
  }

  /**
   * Returns a string containing the string representation of each of {@code parts}, using the
   * previously configured separator between each.
   */
  public final String join(Iterable<?> parts) {
    return join(parts.iterator());
  }

  /**
   * Returns a string containing the string representation of each of {@code parts}, using the
   * previously configured separator between each.
   *
   * @since 11.0
   */
  public final String join(Iterator<?> parts) {
    return appendTo(new StringBuilder(), parts).toString();
  }

  /**
   * Returns a string containing the string representation of each of {@code parts}, using the
   * previously configured separator between each.
   */
  public final String join(Object[] parts) {
    return join(Arrays.asList(parts));
  }

  /**
   * Returns a string containing the string representation of each argument, using the previously
   * configured separator between each.
   */
  public final String join(@Nullable Object first, @Nullable Object second, Object... rest) {
    return join(iterable(first, second, rest));
  }

  /**
   * Returns a joiner with the same behavior as this one, except automatically substituting {@code
   * nullText} for any provided null elements.
   */
  public Joiner useForNull(final String nullText) {
    checkNotNull(nullText);
    return new Joiner(this) {
      @Override CharSequence toString(@Nullable Object part) {
        return (part == null) ? nullText : Joiner.this.toString(part);
      }

      @Override
      public Joiner useForNull(String nullText) {
        throw new UnsupportedOperationException("already specified useForNull");
      }

      @Override
      public Joiner skipNulls() {
        throw new UnsupportedOperationException("already specified useForNull");
      }
    };
  }

  /**
   * Returns a joiner with the same behavior as this joiner, except automatically skipping over any
   * provided null elements.
   */
  public Joiner skipNulls() {
    return new Joiner(this) {
      @Override
      public <A extends Appendable> A appendTo(A appendable, Iterator<?> parts)
          throws IOException {
        checkNotNull(appendable, "appendable");
        checkNotNull(parts, "parts");
        while (parts.hasNext()) {
          Object part = parts.next();
          if (part != null) {
            appendable.append(Joiner.this.toString(part));
            break;
          }
        }
        while (parts.hasNext()) {
          Object part = parts.next();
          if (part != null) {
            appendable.append(separator);
            appendable.append(Joiner.this.toString(part));
          }
        }
        return appendable;
      }

      @Override
      public Joiner useForNull(String nullText) {
        throw new UnsupportedOperationException("already specified skipNulls");
      }
    };
  }

  CharSequence toString(Object part) {
    checkNotNull(part);  // checkNotNull for GWT (do not optimize).
    return part instanceof CharSequence ? (CharSequence) part : part.toString();
  }

  private static Iterable<Object> iterable(
      final Object first, final Object second, final Object[] rest) {
    checkNotNull(rest);
    return new AbstractList<Object>() {
      @Override
      public int size() {
        return rest.length + 2;
      }

      @Override
      public Object get(int index) {
        switch (index) {
          case 0:
            return first;
          case 1:
            return second;
          default:
            return rest[index - 2];
        }
      }
    };
  }
}
