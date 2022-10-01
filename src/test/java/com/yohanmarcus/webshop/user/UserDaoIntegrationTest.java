package com.yohanmarcus.webshop.user;

import com.yohanmarcus.webshop.user.dao.UserDao;
import com.yohanmarcus.webshop.user.dao.UserDaoImpl;
import com.yohanmarcus.webshop.user.domain.User;
import com.yohanmarcus.webshop.user.domain.UserRole;
import com.yohanmarcus.webshop.util.IntegrationTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class UserDaoIntegrationTest extends IntegrationTest {

  private final UserDao userDao = new UserDaoImpl();

  @BeforeEach
  void beforeEach() throws SQLException {
    userDao.removeAll();
  }

  private User generateUser() {
    return User.of(null, "test", "password", UserRole.ADMIN);
  }

  private User generateRandomUser() {
    return User.of(
        null, UUID.randomUUID().toString(), UUID.randomUUID().toString(), UserRole.ADMIN);
  }

  @Test
  void testUserFindAll_returnsAllUsers() throws SQLException {
    for (int i = 0; i < 100; i++) {
      userDao.create(generateRandomUser());
    }
    assertEquals(100, userDao.findAll().size());
  }

  @Test
  void testUserDaoUpdate_updatesExistingItem() throws SQLException {
    User user = generateUser();
    userDao.create(user);
    User gotUser = userDao.findByUsername(user.getUsername()).get();
    gotUser.setRole(UserRole.USER);
    userDao.update(gotUser);
    gotUser = userDao.findByUsername(user.getUsername()).get();
    assertEquals(UserRole.USER, gotUser.getRole());
  }

  @Test
  void testUserDaoRemove_deletesItem() throws SQLException {
    User user = generateUser();
    userDao.create(user);
    User gotUser = userDao.findByUsername(user.getUsername()).get();

    userDao.removeById(gotUser.getId());
    Optional<User> gotUser2 = userDao.findById(gotUser.getId());
    assertTrue(gotUser2.isEmpty());
  }
}
