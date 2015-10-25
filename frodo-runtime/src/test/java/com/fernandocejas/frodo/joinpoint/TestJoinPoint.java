package com.fernandocejas.frodo.joinpoint;

import java.lang.reflect.Method;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.reflect.MethodSignature;
import org.aspectj.lang.reflect.SourceLocation;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.BDDMockito.given;

class TestJoinPoint implements JoinPoint {

  @Mock private MethodSignature methodSignature;
  @Mock private Method method;

  private final Class declaringType;
  private final String methodName;
  private final Class[] methodParameterTypes;
  private final String[] methodParameterNames;
  private final Object[] methodParameterValues;

  private TestJoinPoint(JoinPointBuilder builder) {
    this.declaringType = builder.declaringType;
    this.methodName = builder.methodName;
    this.methodParameterTypes = builder.methodParameterTypes;
    this.methodParameterNames = builder.methodParameterNames;
    this.methodParameterValues = builder.methodParameterValues;

    MockitoAnnotations.initMocks(this);
    given(method.getGenericReturnType()).willReturn(declaringType);
    given(methodSignature.getDeclaringType()).willReturn(declaringType);
    given(methodSignature.getName()).willReturn(methodName);
    given(methodSignature.getParameterTypes()).willReturn(methodParameterTypes);
    given(methodSignature.getParameterNames()).willReturn(methodParameterNames);
    given(methodSignature.getMethod()).willReturn(method);
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

  static class JoinPointBuilder {
    private final Class declaringType;
    private final String methodName;

    private Class[] methodParameterTypes;
    private String[] methodParameterNames;
    private Object[] methodParameterValues;

    JoinPointBuilder(Class declaringType, String methodName) {
      this.declaringType = declaringType;
      this.methodName = methodName;
    }

    JoinPointBuilder withParamTypes(Class... paramTypes) {
      this.methodParameterTypes = paramTypes;
      return this;
    }

    JoinPointBuilder withParamNames(String... paramNames) {
      this.methodParameterNames = paramNames;
      return this;
    }

    JoinPointBuilder withParamValues(Object... paramValues) {
      this.methodParameterValues = paramValues;
      return this;
    }

    TestJoinPoint build() {
      return new TestJoinPoint(this);
    }
  }
}
