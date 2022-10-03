package com.yohanmarcus.webshop.util;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class TransactionFactoryImpl implements TransactionFactory {
  @Override
  public TransactionManager begin() {
    return new TransactionManagerImpl();
  }
}
