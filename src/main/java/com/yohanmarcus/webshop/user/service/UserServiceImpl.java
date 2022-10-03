package com.yohanmarcus.webshop.user.service;

import com.yohanmarcus.webshop.exception.InvalidFormException;
import com.yohanmarcus.webshop.user.dao.UserDao;
import com.yohanmarcus.webshop.user.domain.User;
import com.yohanmarcus.webshop.user.domain.UserRole;
import com.yohanmarcus.webshop.user.form.UserForm;
import com.yohanmarcus.webshop.util.TransactionFactory;
import com.yohanmarcus.webshop.util.TransactionManager;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.sql.SQLException;
import java.util.Optional;

@ApplicationScoped
public class UserServiceImpl implements UserService {

  private final UserDao userDao;
  private final TransactionFactory transactionFactory;

  @Inject
  public UserServiceImpl(UserDao userDao, TransactionFactory transactionFactory) {
    this.userDao = userDao;
    this.transactionFactory = transactionFactory;
  }

  public void registerUser(UserForm form) throws SQLException {
    TransactionManager tm = transactionFactory.begin();
    try {
      // check form validation
      if (!form.isValid()) throw new InvalidFormException("Form is invalid");

      // check that username is unique
      Optional<User> userFromUsername = userDao.findByUsername(form.username(), tm.getConn());
      if (userFromUsername.isPresent()) throw new InvalidFormException("Username is not unique");

      // register user
      User newUser = User.of(null, form.username(), form.password(), UserRole.USER);
      userDao.create(newUser, tm.getConn());

      // commit
      tm.commit();
    } finally {
      tm.close();
    }
  }

  public User loginUser(UserForm form) throws SQLException {
    TransactionManager tm = transactionFactory.begin();
    try {
      // check form validation
      if (!form.isValid()) throw new InvalidFormException("Form is invalid");

      // check that username is unique
      User foundUser =
          userDao
              .findByUsername(form.username(), tm.getConn())
              .orElseThrow(() -> new InvalidFormException("User with username does not exist"));

      // register user
      if (!form.password().equals(foundUser.getPassword()))
        throw new InvalidFormException("Invalid password");

      // commit and return user dto
      tm.commit();

      return foundUser;
    } finally {
      tm.close();
    }
  }
}
