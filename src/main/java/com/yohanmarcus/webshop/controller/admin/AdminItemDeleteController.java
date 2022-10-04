package com.yohanmarcus.webshop.controller.admin;

import com.yohanmarcus.webshop.item.service.ItemService;
import lombok.NoArgsConstructor;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@NoArgsConstructor
@WebServlet(name = "adminItemDeleteServlet", value = "/admin/item/delete")
public class AdminItemDeleteController extends HttpServlet {
  @Inject private ItemService itemService;

  public AdminItemDeleteController(ItemService itemService) {
    this.itemService = itemService;
  }

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    try {
      String id = req.getParameter("id");
      itemService.removeById(id);
      resp.sendRedirect("/admin/item");
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }
}
