package com.sandbox.compiler;

import java.util.Arrays;
import java.util.Locale;
import java.util.logging.Logger;

import javax.inject.Inject;
import javax.tools.JavaCompiler;
import javax.tools.JavaCompiler.CompilationTask;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;

import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import com.sandbox.compiler.CompilerModule.ClassOutputFolder;

public class CompilerServiceImpl implements CompilerService {
  private static final Logger logger = Logger.getLogger(CompilerServiceImpl.class.getName());

  private final JavaCompiler javaCompiler;
  private final String classOutFolder;

  @Inject
  public CompilerServiceImpl(JavaCompiler javaCompiler, @ClassOutputFolder String classOutputFolder) {
    Preconditions.checkNotNull(javaCompiler, "javaCompiler");
    Preconditions.checkArgument(!Strings.isNullOrEmpty(classOutputFolder), "classOutputFolder");

    this.javaCompiler = javaCompiler;
    this.classOutFolder = classOutputFolder;
  }

  @Override
  public void compile(JavaFileObject javFileObject) {
    Preconditions.checkNotNull(javFileObject, "javFileObject");

    Iterable<? extends JavaFileObject> files = Arrays.asList(javFileObject);
    StandardJavaFileManager fileManager =
        javaCompiler.getStandardFileManager(null, Locale.ENGLISH, null);

    // specify classes output folder
    Iterable<String> options = Arrays.asList("-d", this.classOutFolder);
    CompilationTask task = javaCompiler.getTask(null, fileManager, null, options, null, files);
    Boolean result = task.call();
    if (result == true) {
      logger.info("Succeeded");
    }
  }
}
