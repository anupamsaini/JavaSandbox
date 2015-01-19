package com.sandbox.execute;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.inject.Inject;

import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import com.sandbox.compiler.CompilerModule.ClassOutputFolder;

public class ExecutionServiceImpl implements ExecutionService {

  private static final Logger logger = Logger.getLogger(ExecutionServiceImpl.class.getName());

  private final String classOutputFolder;

  @Inject
  public ExecutionServiceImpl(@ClassOutputFolder String classOutputFolder) {
    Preconditions.checkArgument(!Strings.isNullOrEmpty(classOutputFolder), "classOutputFolder");
    this.classOutputFolder = classOutputFolder;
  }
  @Override
  public void execute(String className) {
    Preconditions.checkArgument(!Strings.isNullOrEmpty(className),
        "ClassName can not be null or empty");
    // Create a File object on the root of the directory
    // containing the class file
    File file = new File(this.classOutputFolder);

    try {
      // Convert File to a URL file:/tmp
      URL[] urls = new URL[] {file.toURI().toURL()};

      // Create a new class loader with the directory
      ClassLoader loader = new URLClassLoader(urls);

      // Load in the class; Class.childclass should be located in
      // the directory file:/class/demo/
      Class<?> thisClass = loader.loadClass(className);

      Class[] argTypes = new Class[] {String[].class};
      Method main = thisClass.getDeclaredMethod("main", argTypes);
      String[] mainArgs = {};
      main.invoke(null, (Object) mainArgs);
    } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException
        | MalformedURLException | ClassNotFoundException | NoSuchMethodException
        | SecurityException e) {
      logger.log(Level.SEVERE, "Exception occured while executing the class: " + className, e);
    }
  }

}
