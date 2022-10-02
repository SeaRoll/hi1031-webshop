package com.yohanmarcus.webshop.user.dao;

import com.yohanmarcus.webshop.user.domain.User;
import com.yohanmarcus.webshop.util.Dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Optional;

public interface UserDao extends Dao<User, String> {

  /**
   * Get user by username
   *
   * @param username username to find
   * @return (maybe) found user
   * @throws SQLException sql error
   */
  Optional<User> findByUsername(String username) throws SQLException;

  /**
   * Get by username inside a transaction
   *
   * @param username username to find
   * @param optionalConn transaction connection
   * @return (maybe) found user
   * @throws SQLException sql error
   */
  Optional<User> findByUsername(String username, Connection optionalConn) throws SQLException;
}
