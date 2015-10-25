package com.fernandocejas.frodo.joinpoint;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.reflect.MethodSignature;
import org.aspectj.lang.reflect.SourceLocation;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.BDDMockito.given;

public class TestJoinPoint implements JoinPoint {

  @Mock private MethodSignature methodSignature;

  private final Class declaringType;
  private final String methodName;
  private final Class methodReturnType;
  private final Class[] methodParameterTypes;
  private final String[] methodParameterNames;
  private final Object[] methodParameterValues;


  private TestJoinPoint(Builder builder) {
    this.declaringType = builder.declaringType;
    this.methodName = builder.methodName;
    this.methodReturnType = builder.methodReturnType;
    this.methodParameterTypes = builder.methodParameterTypes;
    this.methodParameterNames = builder.methodParameterNames;
    this.methodParameterValues = builder.methodParameterValues;

    MockitoAnnotations.initMocks(this);
    given(methodSignature.getDeclaringType()).willReturn(declaringType);
    given(methodSignature.getName()).willReturn(methodName);
    given(methodSignature.getParameterTypes()).willReturn(methodParameterTypes);
    given(methodSignature.getParameterNames()).willReturn(methodParameterNames);
    given(methodSignature.getReturnType()).willReturn(methodReturnType);
  }

  @Override public String toShortString() {
    return methodName;
  }

  @Override public String toLongString() {
    return declaringType.getSimpleName() + "#" + methodName;
  }

  @Override public Object getThis() {
    return this;
  }

  @Override public Object getTarget() {
    return methodName;
  }

  @Override public Object[] getArgs() {
    return methodParameterValues;
  }

  @Override public Signature getSignature() {
    return methodSignature;
  }

  @Override public SourceLocation getSourceLocation() {
    return null;
  }

  @Override public String getKind() {
    return null;
  }

  @Override public StaticPart getStaticPart() {
    return null;
  }

  public static class Builder {
    private final Class declaringType;
    private final String methodName;

    private Class methodReturnType;
    private Class[] methodParameterTypes;
    private String[] methodParameterNames;
    private Object[] methodParameterValues;

    public Builder(Class declaringType, String methodName) {
      this.declaringType = declaringType;
      this.methodName = methodName;
    }

    public Builder withReturnType(Class returnType) {
      this.methodReturnType = returnType;
      return this;
    }

    public Builder withParamTypes(Class... paramTypes) {
      this.methodParameterTypes = paramTypes;
      return this;
    }

    public Builder withParamNames(String... paramNames) {
      this.methodParameterNames = paramNames;
      return this;
    }

    public Builder withParamValues(Object... paramValues) {
      this.methodParameterValues = paramValues;
      return this;
    }

    public TestJoinPoint build() {
      return new TestJoinPoint(this);
    }
  }
}
