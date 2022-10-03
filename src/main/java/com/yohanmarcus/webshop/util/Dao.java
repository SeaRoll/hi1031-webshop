package com.yohanmarcus.webshop.util;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface Dao<T, V> {

  /**
   * Finds all dao item with optional external transaction if the `optionalConn` is not null, it
   * will use that as the database connection
   *
   * @param optionalConn optional connection
   * @return all dao items
   * @throws SQLException sql error
   */
  List<T> findAll(Connection optionalConn) throws SQLException;

  /**
   * Finds a dao item by given dao id with optional external transaction if the `optionalConn` is
   * not null, it * will use that as the database connection
   *
   * @param id id to find
   * @param optionalConn optional connection
   * @return found dao item
   * @throws SQLException sql error
   */
  Optional<T> findById(V id, Connection optionalConn) throws SQLException;

  /**
   * Creates a dao item by given dao id with optional external transaction if the `optionalConn` is
   * not null, it * will use that as the database connection
   *
   * @param item item to create
   * @param optionalConn optional connection
   * @throws SQLException sql error
   */
  V create(T item, Connection optionalConn) throws SQLException;

  /**
   * Updates a dao item by given dao id with optional external transaction if the `optionalConn` is
   * not null, it * will use that as the database connection
   *
   * @param item item to update
   * @param optionalConn optional connection
   * @throws SQLException sql error
   */
  T update(T item, Connection optionalConn) throws SQLException;

  /**
   * Removes a dao item by given dao id with optional external transaction if the `optionalConn` is
   * not null, it * will use that as the database connection
   *
   * @param id id of item to delete
   * @param optionalConn optional connection
   * @throws SQLException sql error
   */
  V removeById(V id, Connection optionalConn) throws SQLException;

  /**
   * Removes all dao items with optional external transaction
   *
   * @param optionalConn optional connection
   * @throws SQLException sql error
   */
  void removeAll(Connection optionalConn) throws SQLException;
}
