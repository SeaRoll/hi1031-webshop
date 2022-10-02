package com.yohanmarcus.webshop.user;

import com.yohanmarcus.webshop.exception.InvalidFormException;
import com.yohanmarcus.webshop.user.dao.UserDao;
import com.yohanmarcus.webshop.user.domain.User;
import com.yohanmarcus.webshop.user.domain.UserRole;
import com.yohanmarcus.webshop.user.form.UserForm;
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
    UserForm registerForm = new UserForm("as", "noteng");

    TransactionManager tm = mock(TransactionManager.class);
    when(mockTM.begin()).thenReturn(tm);

    assertThrows(InvalidFormException.class, () -> userService.registerUser(registerForm));

    verify(tm).close();
  }

  @Test
  void testRegisterUser_existingUserThrows() throws SQLException {
    UserForm registerForm = new UserForm("validUser", "validPassword");

    TransactionManager tm = mock(TransactionManager.class);
    when(mockTM.begin()).thenReturn(tm);
    when(mockDao.findByUsername(eq("validUser"), any()))
        .thenReturn(Optional.of(User.of("1", "validUser", "password", UserRole.USER)));

    assertThrows(InvalidFormException.class, () -> userService.registerUser(registerForm));

    verify(tm).close();
  }

  @Test
  void testRegisterUser_savesValidUser() throws SQLException {
    UserForm registerForm = new UserForm("validUser", "validPassword");

    TransactionManager tm = mock(TransactionManager.class);
    when(mockTM.begin()).thenReturn(tm);
    when(mockDao.findByUsername(eq("validUser"), any())).thenReturn(Optional.empty());

    userService.registerUser(registerForm);

    verify(tm).commit();
    verify(tm).close();
  }

  @Test
  void testLoginUser_invalidFormThrows() {
    UserForm registerForm = new UserForm("as", "noteng");

    TransactionManager tm = mock(TransactionManager.class);
    when(mockTM.begin()).thenReturn(tm);

    assertThrows(InvalidFormException.class, () -> userService.registerUser(registerForm));

    verify(tm).close();
  }

  @Test
  void testLoginUser_nonExistingUserThrows() throws SQLException {
    UserForm loginForm = new UserForm("validUser", "validPassword");

    TransactionManager tm = mock(TransactionManager.class);
    when(mockTM.begin()).thenReturn(tm);
    when(mockDao.findByUsername(eq("validUser"), any())).thenReturn(Optional.empty());

    assertThrows(InvalidFormException.class, () -> userService.loginUser(loginForm));

    verify(tm).close();
  }

  @Test
  void testLoginUser_invalidPasswordThrows() throws SQLException {
    UserForm loginForm = new UserForm("validUser", "validPassword");

    TransactionManager tm = mock(TransactionManager.class);
    when(mockTM.begin()).thenReturn(tm);
    when(mockDao.findByUsername(eq("validUser"), any()))
        .thenReturn(Optional.of(User.of("1", "validUser", "validPassword2", UserRole.ADMIN)));

    assertThrows(InvalidFormException.class, () -> userService.loginUser(loginForm));

    verify(tm).close();
  }

  @Test
  void testLoginUser_returnsValidUser() throws SQLException {
    UserForm loginForm = new UserForm("validUser", "validPassword");

    TransactionManager tm = mock(TransactionManager.class);
    when(mockTM.begin()).thenReturn(tm);
    when(mockDao.findByUsername(eq("validUser"), any()))
        .thenReturn(Optional.of(User.of("1", "validUser", "validPassword", UserRole.USER)));

    var userDto = userService.loginUser(loginForm);

    verify(tm).commit();

    assertEquals("1", userDto.getId());
    assertEquals("validUser", userDto.getUsername());
    assertEquals(UserRole.USER, userDto.getRole());

    verify(tm).close();
  }
}
