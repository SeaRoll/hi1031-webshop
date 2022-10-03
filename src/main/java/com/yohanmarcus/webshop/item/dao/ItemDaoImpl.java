package com.yohanmarcus.webshop.item.dao;

import com.yohanmarcus.webshop.item.domain.Item;
import org.apache.commons.dbutils.QueryRunner;

import javax.enterprise.context.ApplicationScoped;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.yohanmarcus.webshop.util.DatabaseConfig.closeConnection;
import static com.yohanmarcus.webshop.util.DatabaseConfig.getConnection;

@ApplicationScoped
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
  public Optional<Item> findById(String id) throws SQLException {
    return findById(id, null);
  }

  @Override
  public Optional<Item> findById(String id, Connection optionalConn) throws SQLException {
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
  public String create(Item item) throws SQLException {
    return create(item, null);
  }

  @Override
  public String create(Item item, Connection optionalConn) throws SQLException {
    Connection conn = null;
    try {
      conn = getConnection(optionalConn);
      QueryRunner run = new QueryRunner();
      String newId = UUID.randomUUID().toString();
      run.update(
          conn,
          "INSERT INTO items (id, name, price, quantity, description, category) VALUES (?, ?, ?, ?, ?, ?)",
          newId,
          item.getName(),
          item.getPrice(),
          item.getQuantity(),
          item.getDescription(),
          item.getCategory());
      return newId;
    } finally {
      closeConnection(conn, optionalConn);
    }
  }

  @Override
  public Item update(Item item) throws SQLException {
    return update(item, null);
  }

  @Override
  public Item update(Item item, Connection optionalConn) throws SQLException {
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
      return item;
    } finally {
      closeConnection(conn, optionalConn);
    }
  }

  @Override
  public String removeById(String id) throws SQLException {
    return removeById(id, null);
  }

  @Override
  public String removeById(String id, Connection optionalConn) throws SQLException {
    Connection conn = null;
    try {
      conn = getConnection(optionalConn);
      QueryRunner run = new QueryRunner();
      run.update(conn, "DELETE FROM items WHERE id = ?", id);
      return id;
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
