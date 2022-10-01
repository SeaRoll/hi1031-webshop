package com.yohanmarcus.webshop.controller;

import com.yohanmarcus.webshop.item.dao.ItemDaoImpl;
import com.yohanmarcus.webshop.item.dto.ItemDto;
import com.yohanmarcus.webshop.item.service.CartService;
import com.yohanmarcus.webshop.item.service.CartServiceImpl;
import com.yohanmarcus.webshop.item.service.ItemService;
import com.yohanmarcus.webshop.item.service.ItemServiceImpl;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet(name = "productServlet", value = "")
public class ProductController extends HttpServlet {

  private ItemService itemService;
  private CartService cartService;

  @Override
  public void init() {
    var itemDao = new ItemDaoImpl();
    itemService = new ItemServiceImpl(itemDao);
    cartService = new CartServiceImpl(itemDao);
  }

  private void processRequest(HttpServletRequest req, HttpServletResponse res)
      throws ServletException, IOException {
    RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/jsp/products.jsp");
    dispatcher.forward(req, res);
  }

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    try {
      List<ItemDto> itemDtoList = itemService.findAll();
      req.setAttribute("items", itemDtoList);
      processRequest(req, resp);
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    try {
      Integer clickedId = Integer.parseInt(req.getParameter("itemId"));
      List<ItemDto> items = (List<ItemDto>) req.getSession().getAttribute("cart");
      List<ItemDto> newItems = cartService.addToCart(clickedId, items);
      req.getSession().setAttribute("cart", newItems);
    } catch (SQLException e) {
      e.printStackTrace();
    } finally {
      resp.sendRedirect("/");
    }
  }
}
