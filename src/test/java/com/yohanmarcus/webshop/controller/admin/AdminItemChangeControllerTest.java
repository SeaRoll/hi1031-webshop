package com.yohanmarcus.webshop.controller.admin;

import com.yohanmarcus.webshop.item.dto.ItemDto;
import com.yohanmarcus.webshop.item.service.ItemService;
import org.junit.jupiter.api.Test;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

import static com.yohanmarcus.webshop.util.JspDispatcher.WEB_INF_JSP_ADMIN_ITEM_CHANGE_JSP;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class AdminItemChangeControllerTest {
  private final ItemService itemService = mock(ItemService.class);
  private final AdminItemChangeController itemController =
      new AdminItemChangeController(itemService);

  @Test
  void testDoGet_rendersPage() throws ServletException, IOException, SQLException {
    HttpServletRequest req = mock(HttpServletRequest.class);
    HttpServletResponse res = mock(HttpServletResponse.class);
    RequestDispatcher reqDispatch = mock(RequestDispatcher.class);

    when(req.getParameter(eq("id"))).thenReturn("1");
    when(itemService.findById(eq("1"))).thenReturn(ItemDto.from("1", "", 2, 2, "", ""));
    when(req.getRequestDispatcher(eq(WEB_INF_JSP_ADMIN_ITEM_CHANGE_JSP))).thenReturn(reqDispatch);

    itemController.doGet(req, res);

    verify(req).setAttribute(eq("item"), any());
    verify(reqDispatch).forward(req, res);
  }

  @Test
  void testDoGet_skipGetOnNullPage() throws ServletException, IOException {
    HttpServletRequest req = mock(HttpServletRequest.class);
    HttpServletResponse res = mock(HttpServletResponse.class);
    RequestDispatcher reqDispatch = mock(RequestDispatcher.class);

    when(req.getParameter(eq("id"))).thenReturn(null);
    when(req.getRequestDispatcher(eq(WEB_INF_JSP_ADMIN_ITEM_CHANGE_JSP))).thenReturn(reqDispatch);

    itemController.doGet(req, res);

    verify(req, times(0)).setAttribute(eq("item"), any());
    verify(reqDispatch).forward(req, res);
  }

  @Test
  void testDoPost_editingCallsRight() throws ServletException, IOException, SQLException {
    HttpServletRequest req = mock(HttpServletRequest.class);
    HttpServletResponse res = mock(HttpServletResponse.class);

    when(req.getParameter(eq("editing"))).thenReturn("true");
    when(req.getParameter(eq("id"))).thenReturn("id");
    when(req.getParameter(eq("name"))).thenReturn("name");
    when(req.getParameter(eq("price"))).thenReturn("1");
    when(req.getParameter(eq("quantity"))).thenReturn("2");
    when(req.getParameter(eq("description"))).thenReturn("test");
    when(req.getParameter(eq("category"))).thenReturn("test");

    itemController.doPost(req, res);

    verify(itemService).update(any());
    verify(res).sendRedirect("/admin/item");
  }

  @Test
  void testDoPost_newCallsRight() throws ServletException, IOException, SQLException {
    HttpServletRequest req = mock(HttpServletRequest.class);
    HttpServletResponse res = mock(HttpServletResponse.class);

    when(req.getParameter(eq("editing"))).thenReturn("false");
    when(req.getParameter(eq("id"))).thenReturn("id");
    when(req.getParameter(eq("name"))).thenReturn("name");
    when(req.getParameter(eq("price"))).thenReturn("1");
    when(req.getParameter(eq("quantity"))).thenReturn("2");
    when(req.getParameter(eq("description"))).thenReturn("test");
    when(req.getParameter(eq("category"))).thenReturn("test");

    itemController.doPost(req, res);

    verify(itemService).create(any());
    verify(res).sendRedirect("/admin/item");
  }
}
