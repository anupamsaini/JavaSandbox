package com.sandbox;

import java.io.IOException;
import java.util.logging.Logger;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.sandbox.compiler.CompilerModule;
import com.sandbox.compiler.CompilerService;
import com.sandbox.execute.ExecutionModule;
import com.sandbox.execute.ExecutionService;
import com.sandbox.io.FileObjectService;
import com.sandbox.io.FileObjectServiceImpl.JavaFileObjectWrapper;
import com.sandbox.io.IOModule;

/**
 * The entry point of dynamic code compilation and running moudle.
 */
public class Main {
  private static final Logger logger = Logger.getLogger(Main.class.getName());

  static StringBuilder contents = new StringBuilder("public class Calculator { "
      + "  public void testAdd() { " + "    System.out.println(22345200+300); " + "  } "
      + "  public static void main(String[] args) { " + "    Calculator cal = new Calculator(); "
      + "    cal.testAdd(); " + "  } " + "} ");

  public static void main(String args[]) throws IOException {
    logger.info("Starting the application.");
    Injector injector =
        Guice.createInjector(new IOModule(), new CompilerModule(), new ExecutionModule());
    FileObjectService fileObjectService = injector.getInstance(FileObjectService.class);

    // 1.Construct an in-memory java source file from your dynamic code
    JavaFileObjectWrapper fileObjectWrapper = fileObjectService.read(contents.toString());

    CompilerService compilerService = injector.getInstance(CompilerService.class);
    // Compiler the source code
    compilerService.compile(fileObjectWrapper.getJavaFileObject());

    // 3.Load your class by URLClassLoader, then instantiate the instance, and call method by
    // reflection
    ExecutionService executionService = injector.getInstance(ExecutionService.class);
    executionService.execute(fileObjectWrapper.getClassName());
    logger.info("Is generated file deleted : " + fileObjectWrapper.getJavaFileObject().delete());
  }
}
