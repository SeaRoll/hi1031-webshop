package com.yohanmarcus.webshop.controller;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter(urlPatterns = "/*")
public class HelloFilter implements Filter {

  private static final String BRUV_JSP = "/WEB-INF/jsp/bruv.jsp";

  @Override
  public void doFilter(
      ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
      throws IOException, ServletException {

    HttpServletRequest httpReq = (HttpServletRequest) servletRequest;
    HttpServletResponse httpResp = (HttpServletResponse) servletResponse;
    HttpSession session = httpReq.getSession();

    if (!httpReq.getMethod().equals("GET") && !httpReq.getMethod().equals("POST")) {
      filterChain.doFilter(servletRequest, servletResponse);
      return;
    }

    /*if (session.getAttribute("hello") == null) {
      System.out.println("This was null! Not using redirect");
      session.setAttribute("hello", "hello");
    } else {
      System.out.println("This was not null. using redirect");
      RequestDispatcher dispatcher = httpReq.getRequestDispatcher(BRUV_JSP);
      dispatcher.forward(httpReq, httpResp);
      return;
    }*/

    filterChain.doFilter(servletRequest, servletResponse);
  }
}
