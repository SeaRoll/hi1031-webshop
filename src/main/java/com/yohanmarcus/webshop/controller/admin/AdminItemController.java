package com.yohanmarcus.webshop.controller.admin;

import com.yohanmarcus.webshop.item.dto.ItemDto;
import com.yohanmarcus.webshop.item.service.ItemService;
import lombok.NoArgsConstructor;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import static com.yohanmarcus.webshop.util.JspDispatcher.WEB_INF_JSP_ADMIN_ITEM_JSP;
import static com.yohanmarcus.webshop.util.JspDispatcher.processRequest;

@NoArgsConstructor
@WebServlet(name = "adminItemServlet", value = "/admin/item")
public class AdminItemController extends HttpServlet {

  @Inject private ItemService itemService;

  public AdminItemController(ItemService itemService) {
    this.itemService = itemService;
  }

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    try {
      List<ItemDto> items = itemService.findAll();
      req.setAttribute("items", items);
      processRequest(req, resp, WEB_INF_JSP_ADMIN_ITEM_JSP);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }
}
