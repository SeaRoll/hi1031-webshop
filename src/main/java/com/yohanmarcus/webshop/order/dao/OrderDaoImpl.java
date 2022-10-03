package com.yohanmarcus.webshop.order.dao;

import com.yohanmarcus.webshop.order.domain.Order;
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
public class OrderDaoImpl implements OrderDao {

  private final OrderHandler orderHandler = new OrderHandler();

  @Override
  public List<Order> findAll(Connection optionalConn) throws SQLException {
    Connection conn = null;
    try {
      conn = getConnection(optionalConn);
      QueryRunner run = new QueryRunner();
      return run.query(conn, "SELECT * FROM orders", orderHandler);
    } finally {
      closeConnection(conn, optionalConn);
    }
  }

  @Override
  public Optional<Order> findById(String id, Connection optionalConn) throws SQLException {
    Connection conn = null;
    try {
      conn = getConnection(optionalConn);
      QueryRunner run = new QueryRunner();
      List<Order> orders = run.query(conn, "SELECT * FROM orders WHERE id = ?", orderHandler, id);
      return orders.size() == 0 ? Optional.empty() : Optional.of(orders.get(0));
    } finally {
      closeConnection(conn, optionalConn);
    }
  }

  @Override
  public String create(Order item, Connection optionalConn) throws SQLException {
    Connection conn = null;
    try {
      conn = getConnection(optionalConn);
      QueryRunner run = new QueryRunner();
      String newId = UUID.randomUUID().toString();
      run.update(
          conn,
          "INSERT INTO orders (id, user_id, status) VALUES (?, ?, CAST(? AS order_status))",
          newId,
          item.getUserId(),
          item.getStatus().toString());
      return newId;
    } finally {
      closeConnection(conn, optionalConn);
    }
  }

  @Override
  public Order update(Order item, Connection optionalConn) throws SQLException {
    Connection conn = null;
    try {
      conn = getConnection(optionalConn);
      QueryRunner run = new QueryRunner();
      run.update(
          conn,
          "UPDATE orders SET user_id = ?, status = CAST(? AS order_status) WHERE id = ?",
          item.getUserId(),
          item.getStatus().toString(),
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
      run.update(conn, "DELETE FROM orders WHERE id = ?", id);
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
      run.update(conn, "DELETE FROM orders");
    } finally {
      closeConnection(conn, optionalConn);
    }
  }

  @Override
  public List<Order> findByUserId(String userId, Connection optionalConn) throws SQLException {
    Connection conn = null;
    try {
      conn = getConnection(optionalConn);
      QueryRunner run = new QueryRunner();
      return run.query(conn, "SELECT * FROM orders WHERE user_id = ?", orderHandler, userId);
    } finally {
      closeConnection(conn, optionalConn);
    }
  }
}
