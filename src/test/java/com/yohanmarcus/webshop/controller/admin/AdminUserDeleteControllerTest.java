package com.yohanmarcus.webshop.controller.admin;

import com.yohanmarcus.webshop.user.service.UserService;
import org.junit.jupiter.api.Test;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

import static org.mockito.Mockito.*;

public class AdminUserDeleteControllerTest {

  private final UserService userService = mock(UserService.class);
  private final AdminUserDeleteController userDeleteController =
      new AdminUserDeleteController(userService);

  @Test
  void doGetTest() throws ServletException, IOException, SQLException {
    HttpServletRequest req = mock(HttpServletRequest.class);
    HttpServletResponse res = mock(HttpServletResponse.class);

    when(req.getParameter(any())).thenReturn("random-admin-id");
    userDeleteController.doGet(req, res);

    verify(userService).removeById(any());
    verify(res).sendRedirect(any());
  }
}
