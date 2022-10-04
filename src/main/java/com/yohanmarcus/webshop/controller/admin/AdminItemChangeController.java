package com.yohanmarcus.webshop.controller.admin;

import com.yohanmarcus.webshop.item.domain.Item;
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

import static com.yohanmarcus.webshop.util.JspDispatcher.WEB_INF_JSP_ADMIN_ITEM_CHANGE_JSP;
import static com.yohanmarcus.webshop.util.JspDispatcher.processRequest;

@NoArgsConstructor
@WebServlet(name = "adminItemChangeServlet", value = "/admin/item/change")
public class AdminItemChangeController extends HttpServlet {
  @Inject private ItemService itemService;

  public AdminItemChangeController(ItemService itemService) {
    this.itemService = itemService;
  }

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    try {
      String id = req.getParameter("id");
      if (id != null) {
        Item item = itemService.findById(id);
        req.setAttribute("item", item);
      }
      processRequest(req, resp, WEB_INF_JSP_ADMIN_ITEM_CHANGE_JSP);
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    try {
      boolean editing = Boolean.parseBoolean(req.getParameter("editing"));
      Item item =
          Item.of(
              req.getParameter("id"),
              req.getParameter("name"),
              Integer.parseInt(req.getParameter("price")),
              Integer.parseInt(req.getParameter("quantity")),
              req.getParameter("description"),
              req.getParameter("category"));
      if (editing) itemService.update(item);
      else itemService.create(item);
      resp.sendRedirect("/admin/item");
    } catch (Exception e) {
      throw new RuntimeException();
    }
  }
}
