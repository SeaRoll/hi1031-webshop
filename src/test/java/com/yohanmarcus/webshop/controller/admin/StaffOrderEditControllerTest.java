package com.yohanmarcus.webshop.controller.admin;

import com.yohanmarcus.webshop.order.domain.OrderStatus;
import com.yohanmarcus.webshop.order.dto.OrderDto;
import com.yohanmarcus.webshop.order.service.OrderService;
import org.junit.jupiter.api.Test;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;

import static com.yohanmarcus.webshop.util.JspDispatcher.WEB_INF_JSP_ADMIN_ORDER_CHANGE_JSP;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class StaffOrderEditControllerTest {

  private final OrderService orderService = mock(OrderService.class);

  private final StaffOrderEditController orderEditController =
      new StaffOrderEditController(orderService);

  @Test
  void testDoGet_rendersPage() throws ServletException, IOException, SQLException {
    HttpServletRequest req = mock(HttpServletRequest.class);
    HttpServletResponse res = mock(HttpServletResponse.class);
    RequestDispatcher reqDispatch = mock(RequestDispatcher.class);

    when(orderService.getOrderById(any()))
        .thenReturn(OrderDto.from("1", "1", OrderStatus.PACKAGING));
    when(req.getRequestDispatcher(eq(WEB_INF_JSP_ADMIN_ORDER_CHANGE_JSP))).thenReturn(reqDispatch);

    orderEditController.doGet(req, res);

    verify(req).setAttribute(eq("order"), any());
    verify(reqDispatch).forward(req, res);
  }

  @Test
  void testDoPost_redirects() throws ServletException, IOException, SQLException {
    HttpServletRequest req = mock(HttpServletRequest.class);
    HttpServletResponse res = mock(HttpServletResponse.class);
    HttpSession session = mock(HttpSession.class);

    when(req.getSession()).thenReturn(session);
    when(req.getParameter("id")).thenReturn("hello");
    when(req.getParameter("status")).thenReturn("PACKAGING");

    orderEditController.doPost(req, res);

    verify(orderService).updateOrderStatus("hello", OrderStatus.PACKAGING);
    verify(res).sendRedirect("/staff/order");
  }
}
