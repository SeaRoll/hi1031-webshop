package com.yohanmarcus.webshop.user.service;

import com.yohanmarcus.webshop.user.domain.UserRole;
import com.yohanmarcus.webshop.user.dto.UserDto;
import com.yohanmarcus.webshop.user.form.UserForm;

import java.sql.SQLException;
import java.util.List;

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
  UserDto loginUser(UserForm form) throws SQLException;

  /**
   * Gets all users
   *
   * @return List of all users
   * @throws SQLException sql error
   */
  List<UserDto> findAll() throws SQLException;

  /**
   * Gets a user
   *
   * @param id user id
   * @return user
   * @throws SQLException sql error
   */
  UserDto findById(String id) throws SQLException;

  /**
   * Updates user values
   *
   * @param id user id
   * @param username user name
   * @param role user role
   * @throws SQLException sql error
   */
  void updateUser(String id, String username, UserRole role) throws SQLException;

  /**
   * Removes a user by id
   *
   * @param id user id
   * @throws SQLException sql error
   */
  void removeById(String id) throws SQLException;
}
