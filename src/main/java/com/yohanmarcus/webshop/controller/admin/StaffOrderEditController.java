package com.yohanmarcus.webshop.controller.admin;

import com.yohanmarcus.webshop.order.domain.Order;
import com.yohanmarcus.webshop.order.domain.OrderStatus;
import com.yohanmarcus.webshop.order.service.OrderService;
import lombok.NoArgsConstructor;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.yohanmarcus.webshop.util.JspDispatcher.WEB_INF_JSP_ADMIN_ORDER_CHANGE_JSP;
import static com.yohanmarcus.webshop.util.JspDispatcher.processRequest;

@NoArgsConstructor
@WebServlet(name = "staffOrderEditServlet", value = "/staff/order/edit")
public class StaffOrderEditController extends HttpServlet {
  @Inject private OrderService orderService;

  public StaffOrderEditController(OrderService orderService) {
    this.orderService = orderService;
  }

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    try {
      String id = req.getParameter("id");
      Order order = orderService.getOrderById(id);
      req.setAttribute("order", order);
      processRequest(req, resp, WEB_INF_JSP_ADMIN_ORDER_CHANGE_JSP);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    try {
      String id = req.getParameter("id");
      OrderStatus orderStatus = OrderStatus.valueOf(req.getParameter("status"));
      orderService.updateOrderStatus(id, orderStatus);
      resp.sendRedirect("/staff/order");
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }
}
