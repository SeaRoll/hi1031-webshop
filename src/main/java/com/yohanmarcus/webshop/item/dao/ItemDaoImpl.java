package com.yohanmarcus.webshop.item.dao;

import com.yohanmarcus.webshop.item.domain.Item;
import org.apache.commons.dbutils.QueryRunner;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import static com.yohanmarcus.webshop.util.DatabaseConfig.closeConnection;
import static com.yohanmarcus.webshop.util.DatabaseConfig.getConnection;

public class ItemDaoImpl implements ItemDao {

  private final ItemHandler itemHandler = new ItemHandler();

  @Override
  public List<Item> findAll() throws SQLException {
    return findAll(null);
  }

  @Override
  public List<Item> findAll(Connection optionalConn) throws SQLException {
    Connection conn = null;
    try {
      conn = getConnection(optionalConn);
      QueryRunner run = new QueryRunner();

      return run.query(conn, "SELECT * FROM items", itemHandler);
    } finally {
      closeConnection(conn, optionalConn);
    }
  }

  @Override
  public Optional<Item> findById(Integer id) throws SQLException {
    return findById(id, null);
  }

  @Override
  public Optional<Item> findById(Integer id, Connection optionalConn) throws SQLException {
    Connection conn = null;
    try {
      conn = getConnection(optionalConn);
      QueryRunner run = new QueryRunner();

      List<Item> items = run.query(conn, "SELECT * FROM items WHERE id = ?", itemHandler, id);
      return items.size() == 0 ? Optional.empty() : Optional.of(items.get(0));
    } finally {
      closeConnection(conn, optionalConn);
    }
  }

  @Override
  public void create(Item item) throws SQLException {
    create(item, null);
  }

  @Override
  public void create(Item item, Connection optionalConn) throws SQLException {
    Connection conn = null;
    try {
      conn = getConnection(optionalConn);
      QueryRunner run = new QueryRunner();
      run.update(
          conn,
          "INSERT INTO items (name, price, quantity, description, category) VALUES (?, ?, ?, ?, ?)",
          item.getName(),
          item.getPrice(),
          item.getQuantity(),
          item.getDescription(),
          item.getCategory());
    } finally {
      closeConnection(conn, optionalConn);
    }
  }

  @Override
  public void update(Item item) throws SQLException {
    update(item, null);
  }

  @Override
  public void update(Item item, Connection optionalConn) throws SQLException {
    Connection conn = null;
    try {
      conn = getConnection(optionalConn);
      QueryRunner run = new QueryRunner();
      run.update(
          conn,
          "UPDATE items SET name = ?, price = ?, quantity = ?, description = ?, category = ? WHERE id = ?",
          item.getName(),
          item.getPrice(),
          item.getQuantity(),
          item.getDescription(),
          item.getCategory(),
          item.getId());
    } finally {
      closeConnection(conn, optionalConn);
    }
  }

  @Override
  public void removeById(Integer id) throws SQLException {
    removeById(id, null);
  }

  @Override
  public void removeById(Integer id, Connection optionalConn) throws SQLException {
    Connection conn = null;
    try {
      conn = getConnection(optionalConn);
      QueryRunner run = new QueryRunner();
      run.update(conn, "DELETE FROM items WHERE id = ?", id);
    } finally {
      closeConnection(conn, optionalConn);
    }
  }

  @Override
  public void removeAll() throws SQLException {
    removeAll(null);
  }

  @Override
  public void removeAll(Connection optionalConn) throws SQLException {
    Connection conn = null;
    try {
      conn = getConnection(optionalConn);
      QueryRunner run = new QueryRunner();
      run.update(conn, "DELETE FROM items");
    } finally {
      closeConnection(conn, optionalConn);
    }
  }
}
