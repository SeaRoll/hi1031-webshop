package com.yohanmarcus.webshop.controller.admin;

import com.yohanmarcus.webshop.user.domain.UserRole;
import com.yohanmarcus.webshop.user.dto.UserDto;
import org.junit.jupiter.api.Test;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class StaffFilterTest {
  private final StaffFilter staffFilter = new StaffFilter();

  @Test
  void testDoFilter_redirectsUser() throws ServletException, IOException {
    HttpServletRequest req = mock(HttpServletRequest.class);
    HttpServletResponse res = mock(HttpServletResponse.class);
    FilterChain filterChain = mock(FilterChain.class);
    HttpSession session = mock(HttpSession.class);

    when(req.getSession()).thenReturn(session);
    when(session.getAttribute("user")).thenReturn(null);

    staffFilter.doFilter(req, res, filterChain);

    verify(res).sendRedirect("/");
  }

  @Test
  void testDoFilter_redirectsUserIfNotAdminOrStaff() throws ServletException, IOException {
    HttpServletRequest req = mock(HttpServletRequest.class);
    HttpServletResponse res = mock(HttpServletResponse.class);
    FilterChain filterChain = mock(FilterChain.class);
    HttpSession session = mock(HttpSession.class);

    when(req.getSession()).thenReturn(session);
    when(session.getAttribute("user")).thenReturn(UserDto.from("1", "s", UserRole.USER));

    staffFilter.doFilter(req, res, filterChain);

    verify(res).sendRedirect("/");
  }

  @Test
  void testDoFilter_doNotRedirectUserIfStaff() throws ServletException, IOException {
    HttpServletRequest req = mock(HttpServletRequest.class);
    HttpServletResponse res = mock(HttpServletResponse.class);
    FilterChain filterChain = mock(FilterChain.class);
    HttpSession session = mock(HttpSession.class);

    when(req.getSession()).thenReturn(session);
    when(session.getAttribute("user")).thenReturn(UserDto.from("1", "s", UserRole.STAFF));

    staffFilter.doFilter(req, res, filterChain);

    verify(filterChain).doFilter(req, res);
  }

  @Test
  void testDoFilter_doNotRedirectUserIfAdmin() throws ServletException, IOException {
    HttpServletRequest req = mock(HttpServletRequest.class);
    HttpServletResponse res = mock(HttpServletResponse.class);
    FilterChain filterChain = mock(FilterChain.class);
    HttpSession session = mock(HttpSession.class);

    when(req.getSession()).thenReturn(session);
    when(session.getAttribute("user")).thenReturn(UserDto.from("1", "s", UserRole.ADMIN));

    staffFilter.doFilter(req, res, filterChain);

    verify(filterChain).doFilter(req, res);
  }
}
