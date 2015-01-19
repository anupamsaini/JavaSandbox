package com.sandbox.compiler;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;

import com.google.inject.AbstractModule;
import com.google.inject.BindingAnnotation;
import com.google.inject.Provides;

public class CompilerModule extends AbstractModule {

  @Override
  protected void configure() {
    bind(CompilerService.class).to(CompilerServiceImpl.class);
    bindConstant().annotatedWith(ClassOutputFolder.class).to("/tmp");
  }

  @Provides
  public JavaCompiler getJavaCompiler() {
    return ToolProvider.getSystemJavaCompiler();
  }

  /**
   * Annotates the URL of the foo server.
   */
  @Retention(RetentionPolicy.RUNTIME)
  @Target({ElementType.FIELD, ElementType.PARAMETER})
  @BindingAnnotation
  public @interface ClassOutputFolder {}
}
