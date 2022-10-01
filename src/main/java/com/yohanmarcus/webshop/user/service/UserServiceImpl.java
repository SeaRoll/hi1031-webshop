package com.yohanmarcus.webshop.user.service;

import com.yohanmarcus.webshop.exception.InvalidFormException;
import com.yohanmarcus.webshop.user.dao.UserDao;
import com.yohanmarcus.webshop.user.domain.User;
import com.yohanmarcus.webshop.user.dto.UserDto;
import com.yohanmarcus.webshop.user.dto.UserFormDto;
import com.yohanmarcus.webshop.util.TransactionManager;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class UserServiceImpl implements UserService {

  private final UserDao userDao;
  private final TransactionManager transactionManager;

  public UserServiceImpl(UserDao userDao, TransactionManager transactionManager) {
    this.userDao = userDao;
    this.transactionManager = transactionManager;
  }

  public List<UserDto> findAll() throws SQLException {
    return userDao.findAll().stream().map(UserDto::toDto).toList();
  }

  public void registerUser(UserFormDto form) throws SQLException {
    TransactionManager tm = transactionManager.begin();
    try {
      // check form validation
      if (!form.isValid()) throw new InvalidFormException("Form is invalid");

      // check that username is unique
      Optional<User> userFromUsername = userDao.getByUsername(form.username(), tm.getConn());
      if (userFromUsername.isPresent()) throw new InvalidFormException("Username is not unique");

      // register user
      User newUser = User.of(null, form.username(), form.password(), "user");
      userDao.create(newUser, tm.getConn());

      // commit
      tm.commit();
    } finally {
      tm.close();
    }
  }

  public UserDto loginUser(UserFormDto form) throws SQLException {
    TransactionManager tm = transactionManager.begin();
    try {
      // check form validation
      if (!form.isValid()) throw new InvalidFormException("Form is invalid");

      // check that username is unique
      User foundUser =
          userDao
              .getByUsername(form.username(), tm.getConn())
              .orElseThrow(() -> new InvalidFormException("User with username does not exist"));

      // register user
      if (!form.password().equals(foundUser.getPassword()))
        throw new InvalidFormException("Invalid password");

      // commit and return user dto
      tm.commit();

      return UserDto.toDto(foundUser);
    } finally {
      tm.close();
    }
  }
}
