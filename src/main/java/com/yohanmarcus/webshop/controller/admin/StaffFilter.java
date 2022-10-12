package com.yohanmarcus.webshop.controller.admin;

import com.yohanmarcus.webshop.user.domain.UserRole;
import com.yohanmarcus.webshop.user.dto.UserDto;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(urlPatterns = "/staff/*")
public class StaffFilter implements Filter {

  @Override
  public void init(FilterConfig filterConfig) throws ServletException {}

  @Override
  public void doFilter(
      ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
      throws IOException, ServletException {
    HttpServletRequest httpReq = (HttpServletRequest) servletRequest;
    HttpServletResponse httpRes = (HttpServletResponse) servletResponse;

    UserDto user = (UserDto) httpReq.getSession().getAttribute("user");
    if (user == null || user.getRole().equals(UserRole.USER)) {
      httpRes.sendRedirect("/");
      return;
    }

    filterChain.doFilter(servletRequest, servletResponse);
  }

  @Override
  public void destroy() {}
}
