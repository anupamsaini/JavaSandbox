package com.sandbox.io;

import java.io.IOException;
import java.net.URI;

import javax.inject.Inject;
import javax.tools.JavaFileObject;
import javax.tools.SimpleJavaFileObject;

import com.google.inject.assistedinject.Assisted;

/**
 * java File Object represents an in-memory java source file <br>
 * so there is no need to put the source file on hard disk
 **/
public class InMemoryJavaFileObject extends SimpleJavaFileObject {
  private final String className;
  private final String contents;

  public interface JavaFileObjectFactory {
    JavaFileObject create(
        @Assisted("className") String className,
        @Assisted("contents") String contents);
  }

  @Inject
  public InMemoryJavaFileObject(
      @Assisted("className") String className,
      @Assisted("contents") String contents) {
    super(URI.create("string:///" + className.replace('.', '/') + Kind.SOURCE.extension),
        Kind.SOURCE);
    this.className = className;
    this.contents = contents;
  }

  public CharSequence getCharContent(boolean ignoreEncodingErrors) throws IOException {
    return contents;
  }

  public String getClassName() {
    return this.className;
  }
}
