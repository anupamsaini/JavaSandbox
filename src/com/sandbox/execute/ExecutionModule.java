package com.sandbox.execute;

import com.google.inject.AbstractModule;

public class ExecutionModule extends AbstractModule {

  @Override
  protected void configure() {
    bind(ExecutionService.class).to(ExecutionServiceImpl.class);
  }
}
