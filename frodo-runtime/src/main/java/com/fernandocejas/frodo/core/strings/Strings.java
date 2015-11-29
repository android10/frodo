package com.fernandocejas.frodo.core.strings;

import java.math.BigInteger;
import java.util.Locale;
import org.jetbrains.annotations.Nullable;

/**
 * General utility functions pertaining to {@link String}s.
 *
 * <p><b>This class contains code derived from <a href="https://github.com/google/guava">Google
 * Guava</a></b>
 */
public final class Strings {

  public static final String EMPTY = "";

  /**
   * Returns the given string if it is non-null; the empty string otherwise.
   *
   * @param string the string to test and possibly return
   * @return {@code string} itself if it is non-null; {@code ""} if it is null
   */
  public static String nullToEmpty(@Nullable CharSequence string) {
    return string == null ? EMPTY : string.toString();
  }

  /**
   * Returns {@code true} if the given string is null or is the empty string.
   *
   * @param string a string reference to check
   * @return {@code true} if the string is null or is the empty string
   */
  public static boolean isNullOrEmpty(@Nullable CharSequence string) {
    return string == null || string.length() == 0;
  }

  /**
   * @return true if the string only contains white space or if {@link #isNullOrEmpty(CharSequence)}
   * would return true; false otherwise.
   */
  public static boolean isBlank(@Nullable CharSequence string) {
    return isNullOrEmpty(nullToEmpty(string).trim());
  }

  /**
   * The converse of {@link #isBlank(CharSequence)}
   */
  public static boolean isNotBlank(@Nullable CharSequence string) {
    return !isBlank(string);
  }

  public static String safeToString(@Nullable Object object) {
    return object == null ? EMPTY : object.toString();
  }

  /**
   * Converts the given bytes to a hexidecimal strings including leading zeros.
   * If you do not need leading zeros, you can use Java's toString(16) instead.
   */
  public static String toHexString(byte[] bytes) {
    return String.format(Locale.US, "%0" + (bytes.length << 1) + "x", new BigInteger(1, bytes));
  }

  /**
   * Returns a joiner which automatically places {@code separator} between consecutive elements.
   */
  public static Joiner joinOn(String separator) {
    return new Joiner(separator);
  }

  /**
   * Returns a joiner which automatically places {@code separator} between consecutive elements.
   */
  public static Joiner joinOn(char separator) {
    return new Joiner(String.valueOf(separator));
  }

  private Strings() {
    // no instances
  }
}
