package com.sandbox.io;

import javax.inject.Inject;
import javax.tools.JavaFileObject;

import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import com.sandbox.io.InMemoryJavaFileObject.JavaFileObjectFactory;

/**
 * Implements the {@link FileObjectService}.
 * 
 */
public class FileObjectServiceImpl implements FileObjectService {
  private static final String ASSUMED_CLASS_NAME_LINE = "public class";
  private static final String CLASS_BODY_START_IDENTIFIER = "{";

  private final JavaFileObjectFactory javaFileObjectFactory;

  @Inject
  public FileObjectServiceImpl(JavaFileObjectFactory javaFileObjectFactory) {
    Preconditions.checkNotNull(javaFileObjectFactory, "javaFileObjectFactory");
    this.javaFileObjectFactory = javaFileObjectFactory;
  }

  public JavaFileObjectWrapper read(String contents) {
    Preconditions.checkNotNull(contents, "contents");

    String className = getClassNameFromStream(contents.toString());
    return new JavaFileObjectWrapper(className, javaFileObjectFactory.create(className,
        contents.toString()));
  }

  /**
   * Extracts the class name from the input stream.
   * 
   * @param javaFile the string representation of java code
   * @return the extracted java file name
   */
  private String getClassNameFromStream(String javaFile) {
    Preconditions.checkNotNull(javaFile);
    int startIndex = javaFile.indexOf(ASSUMED_CLASS_NAME_LINE);
    int endIndex = javaFile.indexOf(CLASS_BODY_START_IDENTIFIER);
    String[] classNameContainer = javaFile.substring(startIndex, endIndex).split(" ");
    if (classNameContainer.length != 3) {
      throw new IllegalArgumentException(
          " FileName not in the expected format: public class MyClass {");
    }
    return classNameContainer[2];
  }

  public static class JavaFileObjectWrapper {
    private final JavaFileObject javaFileObject;
    private final String className;

    public JavaFileObjectWrapper(String clasName, JavaFileObject javaFileObject) {
      Preconditions.checkArgument(!Strings.isNullOrEmpty(clasName), "className");
      Preconditions.checkNotNull(javaFileObject, "javaFileObject");
      this.className = clasName;
      this.javaFileObject = javaFileObject;
    }

    public String getClassName() {
      return this.className;
    }

    public JavaFileObject getJavaFileObject() {
      return this.javaFileObject;
    }
  }
}
