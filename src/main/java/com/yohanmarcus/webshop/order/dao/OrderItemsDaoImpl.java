package com.yohanmarcus.webshop.order.dao;

import com.yohanmarcus.webshop.order.domain.OrderItems;
import com.yohanmarcus.webshop.order.domain.OrderItemsId;
import org.apache.commons.dbutils.QueryRunner;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import static com.yohanmarcus.webshop.util.DatabaseConfig.closeConnection;
import static com.yohanmarcus.webshop.util.DatabaseConfig.getConnection;

public class OrderItemsDaoImpl implements OrderItemsDao {
  private final OrderItemsHandler orderItemsHandler = new OrderItemsHandler();

  @Override
  public List<OrderItems> findAll() throws SQLException {
    return findAll(null);
  }

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
  public Optional<OrderItems> findById(OrderItemsId id) throws SQLException {
    return findById(id, null);
  }

  @Override
  public Optional<OrderItems> findById(OrderItemsId id, Connection optionalConn)
      throws SQLException {
    Connection conn = null;
    try {
      conn = getConnection(optionalConn);
      QueryRunner run = new QueryRunner();
      List<OrderItems> orderItems =
          run.query(
              conn,
              "SELECT * FROM order_items WHERE order_id = ? and item_id = ?",
              orderItemsHandler,
              id.getOrderId(),
              id.getItemId());
      return orderItems.size() == 0 ? Optional.empty() : Optional.of(orderItems.get(0));
    } finally {
      closeConnection(conn, optionalConn);
    }
  }

  @Override
  public OrderItemsId create(OrderItems item) throws SQLException {
    return create(item, null);
  }

  @Override
  public OrderItemsId create(OrderItems item, Connection optionalConn) throws SQLException {
    Connection conn = null;
    try {
      conn = getConnection(optionalConn);
      QueryRunner run = new QueryRunner();
      run.update(
          conn,
          "INSERT INTO order_items (order_id, item_id, quantity) VALUES (?, ?, ?)",
          item.getId().getOrderId(),
          item.getId().getItemId(),
          item.getQuantity());
      return item.getId();
    } finally {
      closeConnection(conn, optionalConn);
    }
  }

  @Override
  public OrderItems update(OrderItems item) throws SQLException {
    return update(item, null);
  }

  @Override
  public OrderItems update(OrderItems item, Connection optionalConn) throws SQLException {
    Connection conn = null;
    try {
      conn = getConnection(optionalConn);
      QueryRunner run = new QueryRunner();
      run.update(
          conn,
          "UPDATE order_items SET order_id = ?, item_id = ?, quantity = ? WHERE order_id = ? and item_id = ?",
          item.getId().getOrderId(),
          item.getId().getItemId(),
          item.getQuantity(),
          item.getId().getOrderId(),
          item.getId().getItemId());
      return item;
    } finally {
      closeConnection(conn, optionalConn);
    }
  }

  @Override
  public OrderItemsId removeById(OrderItemsId id) throws SQLException {
    return removeById(id, null);
  }

  @Override
  public OrderItemsId removeById(OrderItemsId id, Connection optionalConn) throws SQLException {
    Connection conn = null;
    try {
      conn = getConnection(optionalConn);
      QueryRunner run = new QueryRunner();
      run.update(
          conn,
          "DELETE FROM order_items WHERE order_id = ? and item_id = ?",
          id.getOrderId(),
          id.getItemId());
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
      run.update(conn, "DELETE FROM order_items");
    } finally {
      closeConnection(conn, optionalConn);
    }
  }
}
