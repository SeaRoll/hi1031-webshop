package com.yohanmarcus.webshop.controller.admin;

import com.yohanmarcus.webshop.order.service.OrderService;
import lombok.NoArgsConstructor;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

import static com.yohanmarcus.webshop.util.JspDispatcher.WEB_INF_JSP_ADMIN_ORDER_JSP;
import static com.yohanmarcus.webshop.util.JspDispatcher.processRequest;

@NoArgsConstructor
@WebServlet(name = "staffOrderServlet", value = "/staff/order")
public class StaffOrderController extends HttpServlet {
  @Inject private OrderService orderService;

  public StaffOrderController(OrderService orderService) {
    this.orderService = orderService;
  }

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    try {
      var orderWithItems = orderService.getAllOrders();
      req.setAttribute("orders", orderWithItems);
      processRequest(req, resp, WEB_INF_JSP_ADMIN_ORDER_JSP);
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }
}
