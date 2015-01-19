package com.sandbox.io;

import javax.tools.JavaFileObject;

import com.sandbox.io.FileObjectServiceImpl.JavaFileObjectWrapper;

public interface FileObjectService {

  /**
   * Generates a {@link JavaFileObject} from the provided input string.
   * 
   * @param contents the java source file contents
   * @return the {@code JavaFileObject}
   */
  public JavaFileObjectWrapper read(String contents);
}
