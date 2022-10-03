package com.yohanmarcus.webshop.util;

import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

class TransactionManagerIntegrationTest extends IntegrationTest {
  private final TransactionFactory tf = new TransactionFactoryImpl();

  @Test
  void testBeginTransaction_returnsTransactionManager() {
    TransactionManager tm = tf.begin();
    assertEquals(TransactionManagerImpl.class, tm.getClass());
  }

  @Test
  void testCommit() {
    TransactionManager tm = tf.begin();
    assertDoesNotThrow(tm::commit);
  }

  @Test
  void testClose() {
    TransactionManager tm = tf.begin();
    assertDoesNotThrow(tm::close);
  }

  @Test
  void testCommitClose() throws SQLException {
    TransactionManager tm = tf.begin();
    tm.commit();
    assertDoesNotThrow(tm::close);
  }
}
