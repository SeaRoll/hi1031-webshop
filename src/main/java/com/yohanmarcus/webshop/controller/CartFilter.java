package com.yohanmarcus.webshop.controller;

import com.yohanmarcus.webshop.item.domain.Cart;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@WebFilter(urlPatterns = "/*")
public class CartFilter implements Filter {

  @Override
  public void doFilter(
      ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
      throws IOException, ServletException {
    HttpServletRequest httpReq = (HttpServletRequest) servletRequest;

    Cart cart = (Cart) httpReq.getSession().getAttribute("cart");
    if (cart == null) {
      cart = new Cart();
      httpReq.getSession().setAttribute("cart", cart);
    }

    filterChain.doFilter(servletRequest, servletResponse);
  }
}
