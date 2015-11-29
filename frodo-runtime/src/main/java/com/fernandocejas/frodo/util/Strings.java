package com.fernandocejas.frodo.util;

public final class Strings {

  public static String EMPTY = "";

  private Strings() {}

  public static boolean isBlank(String string) {
    return string == null || string.trim().isEmpty();
  }

  public static boolean allInArrayAreBlank(String[] strings) {
    if (strings == null) {
      return true;
    }
    for (String str : strings) {
      if (!isBlank(str)) {
        return false;
      }
    }
    return true;
  }
}
