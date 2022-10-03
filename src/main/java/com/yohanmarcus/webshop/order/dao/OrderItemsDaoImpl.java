package com.yohanmarcus.webshop.order.dao;

import com.yohanmarcus.webshop.order.domain.OrderItems;
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
public class OrderItemsDaoImpl implements OrderItemsDao {
  private final OrderItemsHandler orderItemsHandler = new OrderItemsHandler();

  @Override
  public List<OrderItems> findAll(Connection optionalConn) throws SQLException {
    Connection conn = null;
    try {
      conn = getConnection(optionalConn);
      QueryRunner run = new QueryRunner();

      return run.query(conn, "SELECT * FROM order_items", orderItemsHandler);
    } finally {
      closeConnection(conn, optionalConn);
    }
  }

  @Override
  public Optional<OrderItems> findById(String id, Connection optionalConn) throws SQLException {
    Connection conn = null;
    try {
      conn = getConnection(optionalConn);
      QueryRunner run = new QueryRunner();

      List<OrderItems> orderItems =
          run.query(conn, "SELECT * FROM order_items WHERE id = ?", orderItemsHandler, id);
      return orderItems.size() == 0 ? Optional.empty() : Optional.of(orderItems.get(0));
    } finally {
      closeConnection(conn, optionalConn);
    }
  }

  @Override
  public String create(OrderItems item, Connection optionalConn) throws SQLException {
    Connection conn = null;
    try {
      conn = getConnection(optionalConn);
      QueryRunner run = new QueryRunner();
      String newId = UUID.randomUUID().toString();
      run.update(
          conn,
          "INSERT INTO order_items (id, order_id, name, price, quantity, description, category) VALUES (?, ?, ?, ?, ?, ?, ?)",
          newId,
          item.getOrderId(),
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
  public OrderItems update(OrderItems item, Connection optionalConn) throws SQLException {
    Connection conn = null;
    try {
      conn = getConnection(optionalConn);
      QueryRunner run = new QueryRunner();
      run.update(
          conn,
          "UPDATE order_items SET order_id = ?, name = ?, price = ?, quantity = ?, description = ?, category = ? WHERE id = ?",
          item.getOrderId(),
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
  public String removeById(String id, Connection optionalConn) throws SQLException {
    Connection conn = null;
    try {
      conn = getConnection(optionalConn);
      QueryRunner run = new QueryRunner();
      run.update(conn, "DELETE FROM order_items WHERE id = ?", id);
      return id;
    } finally {
      closeConnection(conn, optionalConn);
    }
  }

  @Override
  public void removeAll(Connection optionalConn) throws SQLException {
    Connection conn = null;
    try {
      conn = getConnection(optionalConn);
      QueryRunner run = new QueryRunner();
      run.update(conn, "DELETE FROM order_items");
    } finally {
      closeConnection(conn, optionalConn);
    }
  }

  @Override
  public List<OrderItems> findByOrderId(String orderId, Connection optionalConn)
      throws SQLException {
    Connection conn = null;
    try {
      conn = getConnection(optionalConn);
      QueryRunner run = new QueryRunner();
      return run.query(
          conn, "SELECT * FROM order_items WHERE order_id = ?", orderItemsHandler, orderId);
    } finally {
      closeConnection(conn, optionalConn);
    }
  }
}
