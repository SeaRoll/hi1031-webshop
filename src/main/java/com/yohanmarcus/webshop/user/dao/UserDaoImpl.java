package com.yohanmarcus.webshop.user.dao;

import com.yohanmarcus.webshop.user.domain.User;
import org.apache.commons.dbutils.QueryRunner;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import static com.yohanmarcus.webshop.util.DatabaseConfig.closeConnection;
import static com.yohanmarcus.webshop.util.DatabaseConfig.getConnection;

public class UserDaoImpl implements UserDao {

  private final UserHandler userHandler = new UserHandler();

  @Override
  public Optional<User> findByUsername(String username) throws SQLException {
    return findByUsername(username, null);
  }

  @Override
  public Optional<User> findByUsername(String username, Connection optionalConn)
      throws SQLException {
    Connection conn = null;
    try {
      conn = getConnection(optionalConn);
      QueryRunner run = new QueryRunner();

      List<User> users =
          run.query(conn, "SELECT * FROM users WHERE username = ?", userHandler, username);
      return users.size() == 0 ? Optional.empty() : Optional.of(users.get(0));
    } finally {
      closeConnection(conn, optionalConn);
    }
  }

  @Override
  public List<User> findAll() throws SQLException {
    return findAll(null);
  }

  @Override
  public List<User> findAll(Connection optionalConn) throws SQLException {
    Connection conn = null;
    try {
      conn = getConnection(optionalConn);
      QueryRunner run = new QueryRunner();

      return run.query(conn, "SELECT * FROM users", userHandler);
    } finally {
      closeConnection(conn, optionalConn);
    }
  }

  @Override
  public Optional<User> findById(Integer id) throws SQLException {
    return findById(id, null);
  }

  @Override
  public Optional<User> findById(Integer id, Connection optionalConn) throws SQLException {
    Connection conn = null;
    try {
      conn = getConnection(optionalConn);
      QueryRunner run = new QueryRunner();

      List<User> users = run.query(conn, "SELECT * FROM users WHERE id = ?", userHandler, id);
      return users.size() == 0 ? Optional.empty() : Optional.of(users.get(0));
    } finally {
      closeConnection(conn, optionalConn);
    }
  }

  @Override
  public void create(User item) throws SQLException {
    create(item, null);
  }

  @Override
  public void create(User item, Connection optionalConn) throws SQLException {
    Connection conn = null;
    try {
      conn = getConnection(optionalConn);
      QueryRunner run = new QueryRunner();
      run.update(
          conn,
          "INSERT INTO users (username, password, role) VALUES (?, ?, ?)",
          item.getUsername(),
          item.getPassword(),
          item.getRole());
    } finally {
      closeConnection(conn, optionalConn);
    }
  }

  @Override
  public void update(User item) throws SQLException {
    update(item, null);
  }

  @Override
  public void update(User item, Connection optionalConn) throws SQLException {
    Connection conn = null;
    try {
      conn = getConnection(optionalConn);
      QueryRunner run = new QueryRunner();
      run.update(
          conn,
          "UPDATE users SET username = ?, password = ?, role = ? WHERE id = ?",
          item.getUsername(),
          item.getPassword(),
          item.getRole(),
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
      run.update(conn, "DELETE FROM users WHERE id = ?", id);
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
      run.update(conn, "DELETE FROM users");
    } finally {
      closeConnection(conn, optionalConn);
    }
  }
}
