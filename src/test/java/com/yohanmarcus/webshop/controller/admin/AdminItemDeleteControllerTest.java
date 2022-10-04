package com.yohanmarcus.webshop.controller.admin;

import com.yohanmarcus.webshop.item.service.ItemService;
import org.junit.jupiter.api.Test;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class AdminItemDeleteControllerTest {
  private final ItemService itemService = mock(ItemService.class);
  private final AdminItemDeleteController itemController =
      new AdminItemDeleteController(itemService);

  @Test
  void testDoGet_rendersPage() throws ServletException, IOException, SQLException {
    HttpServletRequest req = mock(HttpServletRequest.class);
    HttpServletResponse res = mock(HttpServletResponse.class);

    when(req.getParameter(any())).thenReturn("1");
    itemController.doGet(req, res);

    verify(itemService).removeById(any());
    verify(res).sendRedirect(any());
  }
}
