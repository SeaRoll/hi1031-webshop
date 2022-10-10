package com.yohanmarcus.webshop.util;

import javax.enterprise.context.ApplicationScoped;

/** Implementation of transaction factory */
@ApplicationScoped
public class TransactionFactoryImpl implements TransactionFactory {
  @Override
  public TransactionManager begin() {
    return new TransactionManagerImpl();
  }
}
