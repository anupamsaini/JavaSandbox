package com.sandbox.io;

import javax.tools.JavaFileObject;

import com.google.inject.AbstractModule;
import com.google.inject.assistedinject.FactoryModuleBuilder;
import com.sandbox.io.InMemoryJavaFileObject.JavaFileObjectFactory;

public class IOModule extends AbstractModule {

  @Override
  protected void configure() {
    bind(FileObjectService.class).to(FileObjectServiceImpl.class);
    install(new FactoryModuleBuilder()
        .implement(JavaFileObject.class, InMemoryJavaFileObject.class).build(
            JavaFileObjectFactory.class));
  }
}
