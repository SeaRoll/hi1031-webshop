package com.yohanmarcus.webshop.controller;

import com.yohanmarcus.webshop.item.dao.ItemDaoImpl;
import com.yohanmarcus.webshop.item.dto.ItemDto;
import com.yohanmarcus.webshop.item.service.CartService;
import com.yohanmarcus.webshop.item.service.CartServiceImpl;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "cartServlet", value = "/cart")
public class CartController extends HttpServlet {

  private CartService cartService;

  public void init() {
    cartService = new CartServiceImpl(new ItemDaoImpl());
  }

  private void processRequest(HttpServletRequest req, HttpServletResponse res)
      throws ServletException, IOException {
    RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/jsp/cart.jsp");
    dispatcher.forward(req, res);
  }

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    processRequest(req, resp);
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    List<ItemDto> oldItems = (List<ItemDto>) req.getSession().getAttribute("cart");
    Integer clickedId = Integer.parseInt(req.getParameter("itemId"));
    List<ItemDto> newItems = cartService.removeFromCart(clickedId, oldItems);
    req.getSession().setAttribute("cart", newItems);
    resp.sendRedirect("/cart");
  }
}
