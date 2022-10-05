package com.yohanmarcus.webshop.controller.admin;

import com.yohanmarcus.webshop.user.domain.User;
import com.yohanmarcus.webshop.user.domain.UserRole;
import com.yohanmarcus.webshop.user.service.UserService;
import org.junit.jupiter.api.Test;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

import static com.yohanmarcus.webshop.util.JspDispatcher.WEB_INF_JSP_ADMIN_USER_CHANGE_JSP;
import static org.mockito.Mockito.*;

public class AdminUserChangeControllerTest {

  private final UserService userService = mock(UserService.class);
  private final AdminUserChangeController userChangeController =
      new AdminUserChangeController(userService);

  @Test
  void testDoGet() throws ServletException, IOException, SQLException {
    HttpServletRequest req = mock(HttpServletRequest.class);
    HttpServletResponse res = mock(HttpServletResponse.class);
    RequestDispatcher reqDispatch = mock(RequestDispatcher.class);

    when(req.getParameter(eq("id"))).thenReturn("random-admin-id");
    when(userService.findById(eq("random-admin-id")))
        .thenReturn(User.of("random-admin-id", "superadmin", "superadmin", UserRole.ADMIN));
    when(req.getRequestDispatcher(eq(WEB_INF_JSP_ADMIN_USER_CHANGE_JSP))).thenReturn(reqDispatch);

    userChangeController.doGet(req, res);

    verify(req).setAttribute(eq("user"), any());
    verify(reqDispatch).forward(req, res);
  }

  @Test
  void testDoPost_editingCallsRight() throws ServletException, IOException, SQLException {
    HttpServletRequest req = mock(HttpServletRequest.class);
    HttpServletResponse res = mock(HttpServletResponse.class);

    when(req.getParameter(eq("id"))).thenReturn("id");
    when(req.getParameter(eq("username"))).thenReturn("username");
    when(req.getParameter(eq("role"))).thenReturn("USER");

    userChangeController.doPost(req, res);

    verify(userService).updateUser(any(), any(), any());
    verify(res).sendRedirect("/admin/users");
  }
}
