package com.yohanmarcus.webshop.controller.admin;

import com.yohanmarcus.webshop.item.service.ItemService;
import org.junit.jupiter.api.Test;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import static com.yohanmarcus.webshop.util.JspDispatcher.WEB_INF_JSP_ADMIN_ITEM_JSP;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class AdminItemControllerTest {
  private final ItemService itemService = mock(ItemService.class);
  private final AdminItemController itemController = new AdminItemController(itemService);

  @Test
  void testDoGet_rendersPage() throws ServletException, IOException, SQLException {
    HttpServletRequest req = mock(HttpServletRequest.class);
    HttpServletResponse res = mock(HttpServletResponse.class);
    RequestDispatcher reqDispatch = mock(RequestDispatcher.class);

    when(itemService.findAll()).thenReturn(List.of());
    when(req.getRequestDispatcher(eq(WEB_INF_JSP_ADMIN_ITEM_JSP))).thenReturn(reqDispatch);

    itemController.doGet(req, res);

    verify(req).setAttribute(eq("items"), any());
    verify(reqDispatch).forward(req, res);
  }
}
