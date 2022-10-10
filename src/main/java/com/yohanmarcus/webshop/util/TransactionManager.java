package com.yohanmarcus.webshop.util;

import java.sql.Connection;
import java.sql.SQLException;

/** Manages a transaction. */
public interface TransactionManager {

  /**
   * Commits a transaction
   *
   * @throws SQLException sql error
   */
  void commit() throws SQLException;

  /**
   * Closes a transaction. if the commit is called before, it will just close the connection.
   * However, if the transaction was not committed, it will rollback to start point
   */
  void close();

  /**
   * Get connection
   *
   * @return a connection
   */
  Connection getConn();
}
