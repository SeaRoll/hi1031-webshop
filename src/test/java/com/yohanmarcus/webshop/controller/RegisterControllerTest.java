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
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class RegisterControllerTest {
  private final UserService userService = mock(UserService.class);
  private final RegisterController registerController = new RegisterController(userService);

  @Test
  void testDoGet_dispatchesJspOnNonLogin() throws ServletException, IOException {
    HttpServletRequest req = mock(HttpServletRequest.class);
    HttpServletResponse res = mock(HttpServletResponse.class);
    HttpSession session = mock(HttpSession.class);
    RequestDispatcher reqDispatch = mock(RequestDispatcher.class);

    when(req.getSession()).thenReturn(session);
    when(session.getAttribute(eq("user"))).thenReturn(null);
    when(req.getRequestDispatcher(eq("/WEB-INF/jsp/register.jsp"))).thenReturn(reqDispatch);

    registerController.doGet(req, res);

    verify(reqDispatch).forward(req, res);
  }

  @Test
  void testDoGet_redirectsIfLoggedIn() throws ServletException, IOException {
    HttpServletRequest req = mock(HttpServletRequest.class);
    HttpServletResponse res = mock(HttpServletResponse.class);
    HttpSession session = mock(HttpSession.class);

    when(req.getSession()).thenReturn(session);
    when(session.getAttribute(eq("user")))
        .thenReturn(User.of(UUID.randomUUID().toString(), "", "", UserRole.USER));

    registerController.doGet(req, res);

    verify(res).sendRedirect("/");
  }

  @Test
  void testDoPost_redirectsUserOnRegister() throws ServletException, IOException, SQLException {
    HttpServletRequest req = mock(HttpServletRequest.class);
    HttpServletResponse res = mock(HttpServletResponse.class);
    HttpSession session = mock(HttpSession.class);

    when(req.getSession()).thenReturn(session);
    when(req.getParameter(eq("username"))).thenReturn("username");
    when(req.getParameter(eq("password"))).thenReturn("password");

    registerController.doPost(req, res);

    verify(userService).registerUser(any());
    verify(res).sendRedirect("/login");
  }

  @Test
  void testDoPost_addsErrorIfWrongOnService() throws ServletException, IOException, SQLException {
    HttpServletRequest req = mock(HttpServletRequest.class);
    HttpServletResponse res = mock(HttpServletResponse.class);
    RequestDispatcher reqDispatch = mock(RequestDispatcher.class);

    when(req.getParameter(eq("username"))).thenReturn("username");
    when(req.getParameter(eq("password"))).thenReturn("password");
    when(req.getRequestDispatcher(eq("/WEB-INF/jsp/register.jsp"))).thenReturn(reqDispatch);
    doThrow(InvalidFormException.class).when(userService).registerUser(any());

    registerController.doPost(req, res);

    verify(req).setAttribute(eq("error"), any());
    verify(reqDispatch).forward(req, res);
  }
}
