package com.yohanmarcus.webshop.controller.admin;

import com.yohanmarcus.webshop.user.service.UserService;
import org.junit.jupiter.api.Test;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import static com.yohanmarcus.webshop.util.JspDispatcher.WEB_INF_JSP_ADMIN_USERS_JSP;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

public class AdminUserControllerTest {

  private final UserService userService = mock(UserService.class);
  private final AdminUserController userController = new AdminUserController(userService);

  @Test
  void testDoGet() throws ServletException, IOException, SQLException {
    HttpServletRequest req = mock(HttpServletRequest.class);
    HttpServletResponse res = mock(HttpServletResponse.class);
    RequestDispatcher reqDispatch = mock(RequestDispatcher.class);

    when(userService.findAll()).thenReturn(List.of());
    when(req.getRequestDispatcher(eq(WEB_INF_JSP_ADMIN_USERS_JSP))).thenReturn(reqDispatch);

    userController.doGet(req, res);

    verify(req).setAttribute(eq("users"), any());
    verify(reqDispatch).forward(req, res);
  }
}
