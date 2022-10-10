package com.yohanmarcus.webshop.util;

/** Factory that creates a transaction manager */
public interface TransactionFactory {
  /**
   * Begin a transaction
   *
   * @return a new transaction manager
   */
  TransactionManager begin();
}
