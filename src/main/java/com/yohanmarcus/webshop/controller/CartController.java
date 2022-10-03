package com.yohanmarcus.webshop.controller;

import com.yohanmarcus.webshop.item.domain.Cart;
import com.yohanmarcus.webshop.item.service.CartService;
import lombok.NoArgsConstructor;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

import static com.yohanmarcus.webshop.util.JspDispatcher.WEB_INF_JSP_CART_JSP;
import static com.yohanmarcus.webshop.util.JspDispatcher.processRequest;

@NoArgsConstructor
@WebServlet(name = "cartServlet", value = "/cart")
public class CartController extends HttpServlet {
  @Inject private CartService cartService;

  public CartController(CartService cartService) {
    this.cartService = cartService;
  }

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    processRequest(req, resp, WEB_INF_JSP_CART_JSP);
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    try {
      Cart cart = (Cart) req.getSession().getAttribute("cart");
      String clickedId = req.getParameter("itemId");
      cart = cartService.removeFromCart(clickedId, cart);
      req.getSession().setAttribute("cart", cart);
      resp.sendRedirect("/cart");
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }
}
