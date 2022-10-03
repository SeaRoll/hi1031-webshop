package com.yohanmarcus.webshop.util;

public interface TransactionFactory {
  /**
   * Begin a transaction
   *
   * @return a new transaction manager
   */
  TransactionManager begin();
}
