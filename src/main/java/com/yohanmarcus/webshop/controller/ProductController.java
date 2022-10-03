package com.yohanmarcus.webshop.controller;

import com.yohanmarcus.webshop.item.domain.Cart;
import com.yohanmarcus.webshop.item.domain.Item;
import com.yohanmarcus.webshop.item.service.CartService;
import com.yohanmarcus.webshop.item.service.ItemService;
import com.yohanmarcus.webshop.util.JspDispatcher;
import lombok.NoArgsConstructor;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import static com.yohanmarcus.webshop.util.JspDispatcher.WEB_INF_JSP_PRODUCTS_JSP;

@NoArgsConstructor
@WebServlet(name = "productServlet", value = "")
public class ProductController extends HttpServlet {

  @Inject private ItemService itemService;

  @Inject private CartService cartService;

  public ProductController(ItemService itemService, CartService cartService) {
    this.itemService = itemService;
    this.cartService = cartService;
  }

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    try {
      List<Item> itemList = itemService.findAll();
      req.setAttribute("items", itemList);
      JspDispatcher.processRequest(req, resp, WEB_INF_JSP_PRODUCTS_JSP);
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    try {
      String clickedId = req.getParameter("itemId");
      Cart cart = (Cart) req.getSession().getAttribute("cart");
      cart = cartService.addToCart(clickedId, cart);
      req.getSession().setAttribute("cart", cart);
    } catch (SQLException e) {
      e.printStackTrace();
    } finally {
      resp.sendRedirect("/");
    }
  }
}
