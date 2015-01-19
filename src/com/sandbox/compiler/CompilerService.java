package com.sandbox.compiler;

import javax.tools.JavaFileObject;

public interface CompilerService {

  /**
   * Compiles the java source code represented by the {@link JavaFileObject} to {@link Class} file and loads
   * it using a {@link ClassLoader}.
   * @param javFileObject
   */
  public void compile(JavaFileObject javFileObject);
}
