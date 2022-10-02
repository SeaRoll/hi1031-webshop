package com.yohanmarcus.webshop.controller;

import org.junit.jupiter.api.Test;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class LogoutControllerTest {
  private final LogoutController logoutController = new LogoutController();

  @Test
  void testDoGet_logsUserOut() throws IOException {
    HttpServletRequest req = mock(HttpServletRequest.class);
    HttpServletResponse res = mock(HttpServletResponse.class);
    HttpSession session = mock(HttpSession.class);

    when(req.getSession()).thenReturn(session);

    logoutController.doGet(req, res);

    verify(session).setAttribute("user", null);
    verify(res).sendRedirect("/");
  }
}
