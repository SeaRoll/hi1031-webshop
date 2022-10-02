package com.yohanmarcus.webshop.controller;

import com.yohanmarcus.webshop.item.dao.ItemDaoImpl;
import com.yohanmarcus.webshop.item.domain.Cart;
import com.yohanmarcus.webshop.item.service.CartService;
import com.yohanmarcus.webshop.item.service.CartServiceImpl;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "cartServlet", value = "/cart")
public class CartController extends HttpServlet {

  private final CartService cartService;

  public CartController() {
    cartService = new CartServiceImpl(new ItemDaoImpl());
  }

  public CartController(CartService cartService) {
    this.cartService = cartService;
  }

  private void processRequest(HttpServletRequest req, HttpServletResponse res)
      throws ServletException, IOException {
    RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/jsp/cart.jsp");
    dispatcher.forward(req, res);
  }

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    processRequest(req, resp);
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
