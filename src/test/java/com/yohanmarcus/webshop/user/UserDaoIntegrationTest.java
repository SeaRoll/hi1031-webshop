package com.yohanmarcus.webshop.user;

import com.yohanmarcus.webshop.user.dao.UserDao;
import com.yohanmarcus.webshop.user.dao.UserDaoImpl;
import com.yohanmarcus.webshop.user.domain.User;
import com.yohanmarcus.webshop.util.IntegrationTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class UserDaoIntegrationTest extends IntegrationTest {

  private UserDao userDao = new UserDaoImpl();

  @BeforeEach
  void beforeEach() throws SQLException {
    userDao.removeAll();
  }

  private User generateUser() {
    return User.of(null, "test", "password", "admin");
  }

  private User generateRandomUser() {
    return User.of(null, UUID.randomUUID().toString(), UUID.randomUUID().toString(), "admin");
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
    User gotUser = userDao.getByUsername(user.getUsername()).get();
    gotUser.setRole("user");
    userDao.update(gotUser);
    gotUser = userDao.getByUsername(user.getUsername()).get();
    assertEquals("user", gotUser.getRole());
  }

  @Test
  void testItemDaoRemove_deletesItem() throws SQLException {
    User user = generateUser();
    userDao.create(user);
    User gotUser = userDao.getByUsername(user.getUsername()).get();

    userDao.removeById(gotUser.getId());
    Optional<User> gotUser2 = userDao.findById(gotUser.getId());
    assertTrue(gotUser2.isEmpty());
  }
}
