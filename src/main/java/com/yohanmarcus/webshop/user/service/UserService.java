package com.yohanmarcus.webshop.user.service;

import com.yohanmarcus.webshop.user.dto.UserDto;
import com.yohanmarcus.webshop.user.dto.UserFormDto;

import java.sql.SQLException;
import java.util.List;

/** All the methods required to implement a user service. */
public interface UserService {

  /**
   * Get a list of all users
   *
   * @return all users
   * @throws SQLException sql error
   */
  List<UserDto> findAll() throws SQLException;

  /**
   * Register an user
   *
   * @param form form to register user with
   * @throws SQLException sql error
   */
  void registerUser(UserFormDto form) throws SQLException;

  /**
   * Login an user
   *
   * @param form form to login user with
   * @return logged in user
   * @throws SQLException sql error
   */
  UserDto loginUser(UserFormDto form) throws SQLException;
}
