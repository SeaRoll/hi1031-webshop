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
   * Update a user. If a field should not be updated, use the same values as the old one in the dto,
   * otherwise it will be overwritten by this function.
   *
   * @param userToUpdate dto of user to update
   * @throws SQLException sql error
   */
  void updateUser(UserDto userToUpdate) throws SQLException;

  /**
   * Removes a user by id.
   *
   * @param id id of user to remove
   * @throws SQLException sql error
   */
  void removeUser(Integer id) throws SQLException;

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
