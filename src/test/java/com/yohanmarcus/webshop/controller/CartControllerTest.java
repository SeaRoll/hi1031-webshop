package com.yohanmarcus.webshop.controller;

import com.yohanmarcus.webshop.item.dto.CartDto;
import com.yohanmarcus.webshop.item.service.CartService;
import org.junit.jupiter.api.Test;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class CartControllerTest {
  private final CartService cartService = mock(CartService.class);
  private final CartController cartController = new CartController(cartService);

  @Test
  void testDoGet_dispatchesJsp() throws ServletException, IOException {
    HttpServletRequest req = mock(HttpServletRequest.class);
    HttpServletResponse res = mock(HttpServletResponse.class);
    RequestDispatcher reqDispatch = mock(RequestDispatcher.class);

    when(req.getRequestDispatcher(eq("/WEB-INF/jsp/cart.jsp"))).thenReturn(reqDispatch);

    cartController.doGet(req, res);

    verify(reqDispatch).forward(req, res);
  }

  @Test
  void testDoPost_savesAndRedirects() throws SQLException, IOException {
    CartDto cart = CartDto.from(new ArrayList<>());
    HttpServletRequest req = mock(HttpServletRequest.class);
    HttpServletResponse res = mock(HttpServletResponse.class);
    HttpSession session = mock(HttpSession.class);

    when(req.getSession()).thenReturn(session);

    when(session.getAttribute(eq("cart"))).thenReturn(cart);
    when(req.getParameter(eq("itemId"))).thenReturn("1");
    when(cartService.removeFromCart(eq("1"), eq(cart))).thenReturn(cart);

    cartController.doPost(req, res);

    verify(res).sendRedirect("/cart");
    verify(session).setAttribute("cart", cart);
  }
}
