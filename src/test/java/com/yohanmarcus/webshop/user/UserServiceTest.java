package com.yohanmarcus.webshop.user;

import com.yohanmarcus.webshop.exception.InvalidFormException;
import com.yohanmarcus.webshop.user.dao.UserDao;
import com.yohanmarcus.webshop.user.domain.User;
import com.yohanmarcus.webshop.user.dto.UserFormDto;
import com.yohanmarcus.webshop.user.service.UserService;
import com.yohanmarcus.webshop.user.service.UserServiceImpl;
import com.yohanmarcus.webshop.util.TransactionManager;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class UserServiceTest {

  private final TransactionManager mockTM = mock(TransactionManager.class);
  private final UserDao mockDao = mock(UserDao.class);
  private final UserService userService = new UserServiceImpl(mockDao, mockTM);

  @Test
  void testRegisterUser_invalidFormThrows() {
    UserFormDto registerForm = new UserFormDto("as", "noteng");

    TransactionManager tm = mock(TransactionManager.class);
    when(mockTM.begin()).thenReturn(tm);

    assertThrows(InvalidFormException.class, () -> userService.registerUser(registerForm));
  }

  @Test
  void testRegisterUser_existingUserThrows() throws SQLException {
    UserFormDto registerForm = new UserFormDto("validUser", "validPassword");

    TransactionManager tm = mock(TransactionManager.class);
    when(mockTM.begin()).thenReturn(tm);
    when(mockDao.getByUsername(eq("validUser"), any()))
        .thenReturn(Optional.of(User.of(1, "validUser", "password", "user")));

    assertThrows(InvalidFormException.class, () -> userService.registerUser(registerForm));
  }

  @Test
  void testRegisterUser_savesValidUser() throws SQLException {
    UserFormDto registerForm = new UserFormDto("validUser", "validPassword");

    TransactionManager tm = mock(TransactionManager.class);
    when(mockTM.begin()).thenReturn(tm);
    when(mockDao.getByUsername(eq("validUser"), any())).thenReturn(Optional.empty());

    userService.registerUser(registerForm);

    verify(tm).commit();
  }

  @Test
  void testLoginUser_invalidFormThrows() {
    UserFormDto registerForm = new UserFormDto("as", "noteng");

    TransactionManager tm = mock(TransactionManager.class);
    when(mockTM.begin()).thenReturn(tm);

    assertThrows(InvalidFormException.class, () -> userService.registerUser(registerForm));
  }

  @Test
  void testLoginUser_nonExistingUserThrows() throws SQLException {
    UserFormDto loginForm = new UserFormDto("validUser", "validPassword");

    TransactionManager tm = mock(TransactionManager.class);
    when(mockTM.begin()).thenReturn(tm);
    when(mockDao.getByUsername(eq("validUser"), any())).thenReturn(Optional.empty());

    assertThrows(InvalidFormException.class, () -> userService.loginUser(loginForm));
  }

  @Test
  void testLoginUser_returnsValidUser() throws SQLException {
    UserFormDto loginForm = new UserFormDto("validUser", "validPassword");

    TransactionManager tm = mock(TransactionManager.class);
    when(mockTM.begin()).thenReturn(tm);
    when(mockDao.getByUsername(eq("validUser"), any()))
        .thenReturn(Optional.of(User.of(1, "validUser", "validPassword", "user")));

    var userDto = userService.loginUser(loginForm);

    verify(tm).commit();

    assertEquals(1, userDto.id());
    assertEquals("validUser", userDto.username());
    assertEquals("user", userDto.role());
  }
}
