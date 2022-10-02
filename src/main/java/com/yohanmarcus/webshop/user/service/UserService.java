package com.yohanmarcus.webshop.user.service;

import com.yohanmarcus.webshop.user.domain.User;
import com.yohanmarcus.webshop.user.form.UserForm;

import java.sql.SQLException;

/** All the methods required to implement a user service. */
public interface UserService {

  /**
   * Register an user
   *
   * @param form form to register user with
   * @throws SQLException sql error
   */
  void registerUser(UserForm form) throws SQLException;

  /**
   * Login an user
   *
   * @param form form to login user with
   * @return logged in user
   * @throws SQLException sql error
   */
  User loginUser(UserForm form) throws SQLException;
}
