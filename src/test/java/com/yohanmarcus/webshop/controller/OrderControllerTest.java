package com.yohanmarcus.webshop.controller;

import com.yohanmarcus.webshop.item.dto.CartDto;
import com.yohanmarcus.webshop.order.service.OrderService;
import com.yohanmarcus.webshop.user.domain.UserRole;
import com.yohanmarcus.webshop.user.dto.UserDto;
import org.junit.jupiter.api.Test;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class OrderControllerTest {
  private final OrderService orderService = mock(OrderService.class);
  private final OrderController orderController = new OrderController(orderService);

  @Test
  void testDoGet_redirectsNonLoggedIn() throws ServletException, IOException {
    HttpServletRequest req = mock(HttpServletRequest.class);
    HttpServletResponse res = mock(HttpServletResponse.class);
    HttpSession session = mock(HttpSession.class);

    when(req.getSession()).thenReturn(session);
    when(session.getAttribute("user")).thenReturn(null);

    orderController.doGet(req, res);

    verify(res).sendRedirect("/login");
  }

  @Test
  void testDoGet_rendersPage() throws ServletException, IOException {
    HttpServletRequest req = mock(HttpServletRequest.class);
    HttpServletResponse res = mock(HttpServletResponse.class);
    RequestDispatcher reqDispatch = mock(RequestDispatcher.class);
    HttpSession session = mock(HttpSession.class);

    when(req.getSession()).thenReturn(session);
    when(session.getAttribute("user")).thenReturn(UserDto.from("1", "ad", UserRole.USER));
    when(req.getRequestDispatcher(eq("/WEB-INF/jsp/order.jsp"))).thenReturn(reqDispatch);

    orderController.doGet(req, res);

    verify(req).setAttribute(eq("orders"), any());
    verify(reqDispatch).forward(req, res);
  }

  @Test
  void testDoPost_resetsCart() throws ServletException, IOException {
    HttpServletRequest req = mock(HttpServletRequest.class);
    HttpServletResponse res = mock(HttpServletResponse.class);
    HttpSession session = mock(HttpSession.class);

    when(req.getSession()).thenReturn(session);
    when(session.getAttribute("cart")).thenReturn(CartDto.from(new ArrayList<>()));
    when(session.getAttribute("user")).thenReturn(UserDto.from("1", "ad", UserRole.USER));

    orderController.doPost(req, res);

    verify(session).setAttribute(eq("cart"), any());
    verify(res).sendRedirect("/order");
  }

  @Test
  void testDoPost_addErrorOnThrow() throws ServletException, IOException, SQLException {
    HttpServletRequest req = mock(HttpServletRequest.class);
    HttpServletResponse res = mock(HttpServletResponse.class);
    HttpSession session = mock(HttpSession.class);
    RequestDispatcher reqDispatch = mock(RequestDispatcher.class);

    when(req.getSession()).thenReturn(session);
    when(req.getRequestDispatcher(any())).thenReturn(reqDispatch);
    when(session.getAttribute("cart")).thenReturn(CartDto.from(new ArrayList<>()));
    when(session.getAttribute("user")).thenReturn(UserDto.from("1", "ad", UserRole.USER));

    doThrow(IllegalStateException.class).when(orderService).orderItems(any(), any());
    orderController.doPost(req, res);

    verify(req).setAttribute(eq("error"), any());
    verify(reqDispatch).forward(req, res);
  }
}
