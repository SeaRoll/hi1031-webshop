package com.yohanmarcus.webshop.user;

import com.yohanmarcus.webshop.exception.InvalidFormException;
import com.yohanmarcus.webshop.user.dao.UserDao;
import com.yohanmarcus.webshop.user.domain.User;
import com.yohanmarcus.webshop.user.domain.UserRole;
import com.yohanmarcus.webshop.user.dto.UserDto;
import com.yohanmarcus.webshop.user.dto.UserFormDto;
import com.yohanmarcus.webshop.user.service.UserService;
import com.yohanmarcus.webshop.user.service.UserServiceImpl;
import com.yohanmarcus.webshop.util.TransactionManager;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
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

  private User generateRandomUser() {
    return User.of(
        null, UUID.randomUUID().toString(), UUID.randomUUID().toString(), UserRole.ADMIN);
  }

  @Test
  void testRegisterUser_invalidFormThrows() {
    UserFormDto registerForm = new UserFormDto("as", "noteng");

    TransactionManager tm = mock(TransactionManager.class);
    when(mockTM.begin()).thenReturn(tm);

    assertThrows(InvalidFormException.class, () -> userService.registerUser(registerForm));

    verify(tm).close();
  }

  @Test
  void testRegisterUser_existingUserThrows() throws SQLException {
    UserFormDto registerForm = new UserFormDto("validUser", "validPassword");

    TransactionManager tm = mock(TransactionManager.class);
    when(mockTM.begin()).thenReturn(tm);
    when(mockDao.findByUsername(eq("validUser"), any()))
        .thenReturn(Optional.of(User.of(1, "validUser", "password", UserRole.USER)));

    assertThrows(InvalidFormException.class, () -> userService.registerUser(registerForm));

    verify(tm).close();
  }

  @Test
  void testRegisterUser_savesValidUser() throws SQLException {
    UserFormDto registerForm = new UserFormDto("validUser", "validPassword");

    TransactionManager tm = mock(TransactionManager.class);
    when(mockTM.begin()).thenReturn(tm);
    when(mockDao.findByUsername(eq("validUser"), any())).thenReturn(Optional.empty());

    userService.registerUser(registerForm);

    verify(tm).commit();
    verify(tm).close();
  }

  @Test
  void testLoginUser_invalidFormThrows() {
    UserFormDto registerForm = new UserFormDto("as", "noteng");

    TransactionManager tm = mock(TransactionManager.class);
    when(mockTM.begin()).thenReturn(tm);

    assertThrows(InvalidFormException.class, () -> userService.registerUser(registerForm));

    verify(tm).close();
  }

  @Test
  void testLoginUser_nonExistingUserThrows() throws SQLException {
    UserFormDto loginForm = new UserFormDto("validUser", "validPassword");

    TransactionManager tm = mock(TransactionManager.class);
    when(mockTM.begin()).thenReturn(tm);
    when(mockDao.findByUsername(eq("validUser"), any())).thenReturn(Optional.empty());

    assertThrows(InvalidFormException.class, () -> userService.loginUser(loginForm));

    verify(tm).close();
  }

  @Test
  void testLoginUser_invalidPasswordThrows() throws SQLException {
    UserFormDto loginForm = new UserFormDto("validUser", "validPassword");

    TransactionManager tm = mock(TransactionManager.class);
    when(mockTM.begin()).thenReturn(tm);
    when(mockDao.findByUsername(eq("validUser"), any()))
        .thenReturn(Optional.of(User.of(1, "validUser", "validPassword2", UserRole.ADMIN)));

    assertThrows(InvalidFormException.class, () -> userService.loginUser(loginForm));

    verify(tm).close();
  }

  @Test
  void testLoginUser_returnsValidUser() throws SQLException {
    UserFormDto loginForm = new UserFormDto("validUser", "validPassword");

    TransactionManager tm = mock(TransactionManager.class);
    when(mockTM.begin()).thenReturn(tm);
    when(mockDao.findByUsername(eq("validUser"), any()))
        .thenReturn(Optional.of(User.of(1, "validUser", "validPassword", UserRole.USER)));

    var userDto = userService.loginUser(loginForm);

    verify(tm).commit();

    assertEquals(1, userDto.id());
    assertEquals("validUser", userDto.username());
    assertEquals(UserRole.USER, userDto.role());

    verify(tm).close();
  }

  @Test
  void testUpdateUser_throwsOnInvalid() throws SQLException {
    UserDto invalidForm = new UserDto(1, "", UserRole.USER);

    TransactionManager tm = mock(TransactionManager.class);
    when(mockTM.begin()).thenReturn(tm);

    assertThrows(InvalidFormException.class, () -> userService.updateUser(invalidForm));

    verify(tm).close();
  }

  @Test
  void testUpdateUser_throwsOnInvalidId() throws SQLException {
    UserDto invalidForm = new UserDto(1, "validUsername", UserRole.USER);

    TransactionManager tm = mock(TransactionManager.class);
    when(mockTM.begin()).thenReturn(tm);
    when(mockDao.findById(eq(1), any())).thenReturn(Optional.empty());

    Exception ex =
        assertThrows(InvalidFormException.class, () -> userService.updateUser(invalidForm));
    System.out.println(ex.getMessage());

    verify(tm).close();
  }

  @Test
  void testUpdateUser_throwsOnNonUniqueUsername() throws SQLException {
    UserDto invalidForm = new UserDto(1, "validUsername", UserRole.USER);

    TransactionManager tm = mock(TransactionManager.class);
    when(mockTM.begin()).thenReturn(tm);
    when(mockDao.findById(eq(1), any()))
        .thenReturn(Optional.of(User.of(1, "validUsername2", "validPassword", UserRole.USER)));
    when(mockDao.findByUsername(eq("validUsername"), any()))
        .thenReturn(Optional.of(User.of(2, "validUsername", "validPassword", UserRole.USER)));

    Exception ex =
        assertThrows(InvalidFormException.class, () -> userService.updateUser(invalidForm));
    System.out.println(ex.getMessage());

    verify(tm).close();
  }

  @Test
  void testUpdateUser_savesOnValid() throws SQLException {
    UserDto validForm = new UserDto(1, "validUsername", UserRole.USER);

    TransactionManager tm = mock(TransactionManager.class);
    when(mockTM.begin()).thenReturn(tm);
    when(mockDao.findById(eq(1), any()))
        .thenReturn(Optional.of(User.of(1, "validUsername2", "validPassword", UserRole.USER)));
    when(mockDao.findByUsername(eq("validUsername"), any())).thenReturn(Optional.empty());

    userService.updateUser(validForm);

    verify(tm).commit();
    verify(tm).close();
  }

  @Test
  void testFindAllUsers_returnsDto() throws SQLException {
    List<User> users = new ArrayList<>();
    for (int i = 0; i < 100; i++) users.add(generateRandomUser());
    TransactionManager tm = mock(TransactionManager.class);
    when(mockTM.begin()).thenReturn(tm);
    when(mockDao.findAll()).thenReturn(users);

    List<UserDto> userDtos = userService.findAll();

    for (int i = 0; i < 100; i++) {
      UserDto dto = UserDto.toDto(users.get(i));
      assertEquals(userDtos.get(i), dto);
    }
  }

  @Test
  void testRemoveUser_callsDao() throws SQLException {
    assertDoesNotThrow(() -> userService.removeUser(1));
  }
}
