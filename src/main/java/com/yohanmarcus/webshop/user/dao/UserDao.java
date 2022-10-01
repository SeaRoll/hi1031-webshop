package com.yohanmarcus.webshop.user.dao;

import com.yohanmarcus.webshop.user.domain.User;
import com.yohanmarcus.webshop.util.Dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Optional;

public interface UserDao extends Dao<User, Integer> {
  Optional<User> getByUsername(String username) throws SQLException;

  Optional<User> getByUsername(String username, Connection optionalConn) throws SQLException;
}
