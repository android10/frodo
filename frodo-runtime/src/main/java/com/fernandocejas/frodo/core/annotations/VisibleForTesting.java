package com.fernandocejas.frodo.core.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Denotes that the class, method or field has its visibility relaxed, so that it is more widely
 * visible than otherwise necessary to make code testable.
 */
@Retention(RetentionPolicy.SOURCE)
public @interface VisibleForTesting {
}
