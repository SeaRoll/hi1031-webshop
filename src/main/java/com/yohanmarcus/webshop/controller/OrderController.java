package com.yohanmarcus.webshop.controller;

import com.yohanmarcus.webshop.item.dao.ItemDaoImpl;
import com.yohanmarcus.webshop.item.domain.Cart;
import com.yohanmarcus.webshop.order.dao.OrderDaoImpl;
import com.yohanmarcus.webshop.order.dao.OrderItemsDaoImpl;
import com.yohanmarcus.webshop.order.service.OrderService;
import com.yohanmarcus.webshop.order.service.OrderServiceImpl;
import com.yohanmarcus.webshop.user.domain.User;
import com.yohanmarcus.webshop.util.TransactionManagerImpl;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "orderServlet", value = "/order")
public class OrderController extends HttpServlet {

  private final OrderService orderService;

  public OrderController() {
    var orderDao = new OrderDaoImpl();
    var itemDao = new ItemDaoImpl();
    var orderItemsDao = new OrderItemsDaoImpl();
    var transactionManager = new TransactionManagerImpl();
    orderService = new OrderServiceImpl(orderDao, orderItemsDao, itemDao, transactionManager);
  }

  public OrderController(OrderService orderService) {
    this.orderService = orderService;
  }

  private void processCartRequest(HttpServletRequest req, HttpServletResponse res)
      throws ServletException, IOException {
    RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/jsp/cart.jsp");
    dispatcher.forward(req, res);
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    Cart cart = (Cart) req.getSession().getAttribute("cart");
    User user = (User) req.getSession().getAttribute("user");

    try {
      orderService.orderItems(cart, user);
      req.getSession().setAttribute("cart", new Cart());
      resp.sendRedirect("/cart"); // todo: change to /orders
    } catch (Exception e) {
      e.printStackTrace();
      req.setAttribute("error", e.getMessage());
      processCartRequest(req, resp);
    }
  }
}
