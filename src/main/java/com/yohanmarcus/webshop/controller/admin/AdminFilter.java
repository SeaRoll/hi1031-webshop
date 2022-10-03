package com.yohanmarcus.webshop.controller.admin;

import com.yohanmarcus.webshop.user.domain.User;
import com.yohanmarcus.webshop.user.domain.UserRole;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(urlPatterns = "/admin/*")
public class AdminFilter implements Filter {

  @Override
  public void doFilter(
      ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
      throws IOException, ServletException {
    HttpServletRequest httpReq = (HttpServletRequest) servletRequest;
    HttpServletResponse httpRes = (HttpServletResponse) servletResponse;

    User user = (User) httpReq.getSession().getAttribute("user");
    if (user == null || !user.getRole().equals(UserRole.ADMIN)) {
      httpRes.sendRedirect("/");
      return;
    }

    filterChain.doFilter(servletRequest, servletResponse);
  }
}
