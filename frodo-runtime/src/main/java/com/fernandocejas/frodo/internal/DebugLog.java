package com.fernandocejas.frodo.internal;

import android.util.Log;

/**
 * Wrapper around {@link android.util.Log}
 */
class DebugLog {

  DebugLog() {
  }

  /**
   * Send a debug log message
   *
   * @param tag Source of a log message. It usually identifies the class or activity where the log
   * call occurs.
   * @param message The message you would like logged.
   */
  public void log(String tag, String message) {
    Log.d(tag, message);
  }
}
