package com.yohanmarcus.webshop.controller;

import com.yohanmarcus.webshop.item.dto.CartDto;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;

@WebFilter(urlPatterns = "/*")
public class CartFilter implements Filter {

  @Override
  public void init(FilterConfig filterConfig) throws ServletException {}

  @Override
  public void doFilter(
      ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
      throws IOException, ServletException {
    HttpServletRequest httpReq = (HttpServletRequest) servletRequest;

    CartDto cart = (CartDto) httpReq.getSession().getAttribute("cart");
    if (cart == null) {
      cart = CartDto.from(new ArrayList<>());
      httpReq.getSession().setAttribute("cart", cart);
    }

    filterChain.doFilter(servletRequest, servletResponse);
  }

  @Override
  public void destroy() {}
}
