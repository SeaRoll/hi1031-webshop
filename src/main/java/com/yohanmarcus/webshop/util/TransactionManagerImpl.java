package com.yohanmarcus.webshop.util;

import java.sql.Connection;
import java.sql.SQLException;

/** Transaction manager for a connection. provides abstraction to connection management. */
public class TransactionManagerImpl implements TransactionManager {

  private Connection conn;
  private boolean committed;

  protected TransactionManagerImpl() {
    startTransaction();
  }

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

  public void commit() throws SQLException {
    conn.commit();
    committed = true;
  }

  public void close() {
    if (conn == null) return;
    try {
      if (!committed) {
        conn.rollback();
      }
      conn.close();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  public Connection getConn() {
    return conn;
  }
}
