package com.yohanmarcus.webshop.util;

import java.sql.Connection;
import java.sql.SQLException;

/** Transaction manager for a connection. provides abstraction to connection management. */
public class TransactionManagerImpl implements TransactionManager {

  private Connection conn;
  private boolean committed;

  public TransactionManagerImpl() {}

  /** Begin a transaction */
  private void startTransaction() {
    committed = false;
    try {
      conn = DatabaseConfig.getDataSource().getConnection();
      conn.setAutoCommit(false);
    } catch (SQLException e) {
      close();
      throw new RuntimeException(e);
    }
  }

  public TransactionManager begin() {
    TransactionManagerImpl newTransactionManager = new TransactionManagerImpl();
    newTransactionManager.startTransaction();
    return newTransactionManager;
  }

  /**
   * Commits a transaction
   *
   * @throws SQLException sql error
   */
  public void commit() throws SQLException {
    conn.commit();
    committed = true;
  }

  /** Closes a transaction connection. */
  public void close() {
    if (conn == null) return;
    try {
      if (!committed) {
        System.out.println("commit was false, rolling back");
        conn.rollback();
      }
      conn.close();
    } catch (SQLException e) {
      e.printStackTrace();
      System.out.println("Tried to close, but failed");
    }
  }

  /**
   * Get connection
   *
   * @return a connection
   */
  public Connection getConn() {
    return conn;
  }
}
