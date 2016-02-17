package com.fernandocejas.frodo.joinpoint;

import android.os.Looper;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.reflect.MethodSignature;

/**
 * Wrapper around {@link org.aspectj.lang.JoinPoint} to make easy retrieve data from a certain
 * {@link org.aspectj.lang.JoinPoint} passed as a parameter in the constructor.
 */
public class FrodoJoinPoint {

  private final JoinPoint joinPoint;
  private final MethodSignature methodSignature;
  private final String classCanonicalName;
  private final String classSimpleName;
  private final String methodName;
  private final List<Class> methodParamTypesList;
  private final List<String> methodParamNamesList;
  private final List<Object> methodParamValuesList;
  private final String executionThreadName;
  private final String joinPointUniqueName;

  /**
   * Constructor of the class
   *
   * @param joinPoint object to wrap around.
   */
  public FrodoJoinPoint(JoinPoint joinPoint) {
    if (joinPoint == null) {
      throw new IllegalArgumentException("Constructor parameters cannot be null!!!");
    }

    this.joinPoint = joinPoint;
    this.methodSignature = (MethodSignature) this.joinPoint.getSignature();
    this.classCanonicalName = this.methodSignature.getDeclaringType().getCanonicalName();
    this.classSimpleName = this.methodSignature.getDeclaringType().getSimpleName();
    this.methodName = this.methodSignature.getName();
    this.executionThreadName = Thread.currentThread().getName();
    final Class[] parameterTypes = this.methodSignature.getParameterTypes();
    final String[] parameterNames = this.methodSignature.getParameterNames();
    final Object[] args = this.joinPoint.getArgs();
    this.methodParamTypesList = parameterTypes != null ?
        Arrays.asList(parameterTypes) : Collections.<Class>emptyList();
    this.methodParamNamesList = parameterNames != null ?
        Arrays.asList(parameterNames) : Collections.<String>emptyList();
    this.methodParamValuesList = args != null ? Arrays.asList(args) : Collections.emptyList();
    this.joinPointUniqueName = this.generateJoinPointUniqueName();
  }

  public String getClassSimpleName() {
    return classSimpleName;
  }

  public String getMethodName() {
    return methodName;
  }

  public List<Class> getMethodParamTypesList() {
    return methodParamTypesList;
  }

  public List<String> getMethodParamNamesList() {
    return methodParamNamesList;
  }

  public List<Object> getMethodParamValuesList() {
    return methodParamValuesList;
  }

  public String getExecutionThreadName() {
    return executionThreadName;
  }

  /**
   * Gets an annotation from a method, will return null in case it does not exist.
   * It is important to have a retention policy of
   * {@link java.lang.annotation.RetentionPolicy#RUNTIME} to avoid null values when reading them.
   *
   * @param annotation The {@link java.lang.annotation.Annotation} to get.
   * @return The annotation if exists, otherwise null.
   */
  public Annotation getAnnotation(Class<? extends Annotation> annotation) {
    return methodSignature.getMethod().getAnnotation(annotation);
  }

  /**
   * Check if the {@link JoinPoint} is being executed in the main thread.
   *
   * @return true: main thread, otherwise false.
   */
  public boolean isMainThread() {
    return (Looper.getMainLooper() == Looper.myLooper());
  }

  /**
   * Check if the {@link JoinPoint} has a return type.
   *
   * @param joinPoint the {@link JoinPoint} to check.
   * @return true if there is a return type, false if it is void.
   */
  public boolean hasReturnType(JoinPoint joinPoint) {
    Signature signature = joinPoint.getSignature();

    return (signature instanceof MethodSignature
        && ((MethodSignature) signature).getReturnType() != void.class);
  }

  /**
   * {@link MethodSignature#getReturnType()}.
   */
  public Class getReturnType() {
    return methodSignature.getReturnType();
  }

  /**
   * {@link Method#getGenericReturnType()}.
   */
  public List<Class> getGenericReturnTypes() {
    final Type returnType = methodSignature.getMethod().getGenericReturnType();
    if (returnType instanceof ParameterizedType) {
      final Type[] typeArguments = ((ParameterizedType) returnType).getActualTypeArguments();
      final List<Class> genericReturnTypes = new ArrayList<>(typeArguments.length);
      for (Type typeArgument : typeArguments) {
        genericReturnTypes.add(typeArgument.getClass());
      }
      return genericReturnTypes;
    }
    final Class clazz = (Class<?>) returnType;
    return Collections.singletonList(clazz);
  }

  /**
   * {@link JoinPoint#getTarget()}.
   */
  public Object getTarget() {
    return joinPoint.getTarget();
  }

  /**
   * Get wrapped {@link JoinPoint}.
   */
  public JoinPoint getJoinPoint() {
    return joinPoint;
  }

  @Override
  public boolean equals(Object object) {
    if (object == this) {
      return true;
    }

    if ((object == null) || (object.getClass() != this.getClass())) {
      return false;
    }

    FrodoJoinPoint joinPoint = (FrodoJoinPoint) object;
    return this.joinPointUniqueName.equals(joinPoint.joinPointUniqueName);
  }

  @Override
  public int hashCode() {
    return this.joinPointUniqueName.hashCode();
  }

  private String generateJoinPointUniqueName() {
    StringBuilder stringBuilder = new StringBuilder(256);
    stringBuilder.append(this.classCanonicalName);
    stringBuilder.append(".");
    stringBuilder.append(this.methodName);
    stringBuilder.append("(");
    if (!this.methodParamNamesList.isEmpty()) {
      for (int i = 0; i < this.methodParamNamesList.size(); i++) {
        stringBuilder.append(this.methodParamTypesList.get(i).getSimpleName());
        stringBuilder.append(" ");
        stringBuilder.append(this.methodParamNamesList.get(i));
        if ((i != this.methodParamNamesList.size() - 1)) {
          stringBuilder.append(", ");
        }
      }
    }
    stringBuilder.append(")");

    return stringBuilder.toString();
  }
}
