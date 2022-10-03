package com.yohanmarcus.webshop.util;

import java.sql.Connection;
import java.sql.SQLException;

public interface TransactionManager {

  /**
   * Commits a transaction
   *
   * @throws SQLException sql error
   */
  void commit() throws SQLException;

  /** Closes a transaction connection. */
  void close();

  /**
   * Get connection
   *
   * @return a connection
   */
  Connection getConn();
}
