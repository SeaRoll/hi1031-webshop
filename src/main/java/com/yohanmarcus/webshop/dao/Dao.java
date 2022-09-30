package com.yohanmarcus.webshop.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface Dao<T, V> {

  List<T> findAll() throws SQLException;

  List<T> findAll(Connection optionalConn) throws SQLException;

  Optional<T> findById(V id) throws SQLException;

  Optional<T> findById(V id, Connection optionalConn) throws SQLException;

  void create(T item) throws SQLException;

  void create(T item, Connection optionalConn) throws SQLException;

  void update(T item) throws SQLException;

  void update(T item, Connection optionalConn) throws SQLException;

  void removeById(V id) throws SQLException;

  void removeById(V id, Connection optionalConn) throws SQLException;

  void removeAll() throws SQLException;

  void removeAll(Connection optionalConn) throws SQLException;
}
