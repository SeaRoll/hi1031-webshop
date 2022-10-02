package com.yohanmarcus.webshop.controller;

import com.yohanmarcus.webshop.exception.InvalidFormException;
import com.yohanmarcus.webshop.user.domain.User;
import com.yohanmarcus.webshop.user.domain.UserRole;
import com.yohanmarcus.webshop.user.service.UserService;
import org.junit.jupiter.api.Test;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class LoginControllerTest {
  private final UserService userService = mock(UserService.class);
  private final LoginController loginController = new LoginController(userService);

  @Test
  void testDoGet_dispatchesJspOnNonLogin() throws ServletException, IOException {
    HttpServletRequest req = mock(HttpServletRequest.class);
    HttpServletResponse res = mock(HttpServletResponse.class);
    HttpSession session = mock(HttpSession.class);
    RequestDispatcher reqDispatch = mock(RequestDispatcher.class);

    when(req.getSession()).thenReturn(session);
    when(session.getAttribute(eq("user"))).thenReturn(null);
    when(req.getRequestDispatcher(eq("/WEB-INF/jsp/login.jsp"))).thenReturn(reqDispatch);

    loginController.doGet(req, res);

    verify(reqDispatch).forward(req, res);
  }

  @Test
  void testDoGet_redirectsIfLoggedIn() throws ServletException, IOException {
    HttpServletRequest req = mock(HttpServletRequest.class);
    HttpServletResponse res = mock(HttpServletResponse.class);
    HttpSession session = mock(HttpSession.class);

    when(req.getSession()).thenReturn(session);
    when(session.getAttribute(eq("user"))).thenReturn(User.of(1, "", "", UserRole.USER));

    loginController.doGet(req, res);

    verify(res).sendRedirect("/");
  }

  @Test
  void testDoPost_redirectsUserOnLogin() throws ServletException, IOException, SQLException {
    User user = User.of(1, "username", "password", UserRole.USER);
    HttpServletRequest req = mock(HttpServletRequest.class);
    HttpServletResponse res = mock(HttpServletResponse.class);
    HttpSession session = mock(HttpSession.class);

    when(req.getSession()).thenReturn(session);
    when(req.getParameter(eq("username"))).thenReturn("username");
    when(req.getParameter(eq("password"))).thenReturn("password");
    when(userService.loginUser(any())).thenReturn(user);

    loginController.doPost(req, res);

    verify(session).setAttribute("user", user);
    verify(res).sendRedirect("/");
  }

  @Test
  void testDoPost_addsErrorIfWrongOnService() throws ServletException, IOException, SQLException {
    HttpServletRequest req = mock(HttpServletRequest.class);
    HttpServletResponse res = mock(HttpServletResponse.class);
    RequestDispatcher reqDispatch = mock(RequestDispatcher.class);

    when(req.getParameter(eq("username"))).thenReturn("username");
    when(req.getParameter(eq("password"))).thenReturn("password");
    when(userService.loginUser(any())).thenThrow(InvalidFormException.class);
    when(req.getRequestDispatcher(eq("/WEB-INF/jsp/login.jsp"))).thenReturn(reqDispatch);

    loginController.doPost(req, res);

    verify(req).setAttribute(eq("error"), any());
    verify(reqDispatch).forward(req, res);
  }
}
