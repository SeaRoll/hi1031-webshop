package com.yohanmarcus.webshop.controller;

import com.yohanmarcus.webshop.item.domain.Cart;
import com.yohanmarcus.webshop.order.service.OrderService;
import com.yohanmarcus.webshop.user.domain.User;
import lombok.NoArgsConstructor;

import javax.inject.Inject;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@NoArgsConstructor
@WebServlet(name = "orderServlet", value = "/order")
public class OrderController extends HttpServlet {

  @Inject private OrderService orderService;

  public OrderController(OrderService orderService) {
    this.orderService = orderService;
  }

  private void processCartRequest(HttpServletRequest req, HttpServletResponse res)
      throws ServletException, IOException {
    RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/jsp/cart.jsp");
    dispatcher.forward(req, res);
  }

  private void processRequest(HttpServletRequest req, HttpServletResponse res)
      throws ServletException, IOException {
    RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/jsp/order.jsp");
    dispatcher.forward(req, res);
  }

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    User user = (User) req.getSession().getAttribute("user");
    if (user == null) {
      resp.sendRedirect("/login");
      return;
    }
    try {
      req.setAttribute("orders", orderService.getOrderByUser(user));
      processRequest(req, resp);
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    Cart cart = (Cart) req.getSession().getAttribute("cart");
    User user = (User) req.getSession().getAttribute("user");

    try {
      orderService.orderItems(cart, user);
      req.getSession().setAttribute("cart", new Cart());
      resp.sendRedirect("/order");
    } catch (Exception e) {
      e.printStackTrace();
      req.setAttribute("error", e.getMessage());
      processCartRequest(req, resp);
    }
  }
}
