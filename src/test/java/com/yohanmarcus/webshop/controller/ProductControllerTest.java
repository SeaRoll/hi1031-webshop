package com.yohanmarcus.webshop.controller;

import com.yohanmarcus.webshop.item.domain.Cart;
import com.yohanmarcus.webshop.item.service.CartService;
import com.yohanmarcus.webshop.item.service.ItemService;
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

class ProductControllerTest {
  private final CartService cartService = mock(CartService.class);
  private final ItemService itemService = mock(ItemService.class);
  private final ProductController productController =
      new ProductController(itemService, cartService);

  @Test
  void testDoGet_dispatchesJsp() throws ServletException, IOException, SQLException {
    HttpServletRequest req = mock(HttpServletRequest.class);
    HttpServletResponse res = mock(HttpServletResponse.class);
    RequestDispatcher reqDispatch = mock(RequestDispatcher.class);

    when(req.getRequestDispatcher(eq("/WEB-INF/jsp/products.jsp"))).thenReturn(reqDispatch);

    productController.doGet(req, res);

    verify(reqDispatch).forward(req, res);
    verify(itemService).findAll();
    verify(req).setAttribute(eq("items"), any());
  }

  @Test
  void testDoPost_savesAndRedirects() throws SQLException, IOException {
    Cart cart = mock(Cart.class);
    HttpServletRequest req = mock(HttpServletRequest.class);
    HttpServletResponse res = mock(HttpServletResponse.class);
    HttpSession session = mock(HttpSession.class);

    when(req.getSession()).thenReturn(session);

    when(session.getAttribute(eq("cart"))).thenReturn(cart);
    when(req.getParameter(eq("itemId"))).thenReturn("1");
    when(cartService.addToCart(eq("1"), eq(cart))).thenReturn(cart);

    productController.doPost(req, res);

    verify(res).sendRedirect("/");
    verify(session).setAttribute("cart", cart);
  }
}
