package com.yohanmarcus.webshop.controller;

import com.yohanmarcus.webshop.user.dao.UserDaoImpl;
import com.yohanmarcus.webshop.user.dto.UserDto;
import com.yohanmarcus.webshop.user.dto.UserFormDto;
import com.yohanmarcus.webshop.user.service.UserService;
import com.yohanmarcus.webshop.user.service.UserServiceImpl;
import com.yohanmarcus.webshop.util.TransactionManagerImpl;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "loginServlet", value = "/login")
public class LoginController extends HttpServlet {

  private UserService userService;

  public void init() {
    userService = new UserServiceImpl(new UserDaoImpl(), new TransactionManagerImpl());
  }

  private void processRequest(HttpServletRequest req, HttpServletResponse res)
      throws ServletException, IOException {
    RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/jsp/login.jsp");
    dispatcher.forward(req, res);
  }

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse res)
      throws ServletException, IOException {
    if (req.getSession().getAttribute("user") != null) {
      res.sendRedirect("/");
      return;
    }
    processRequest(req, res);
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse res)
      throws ServletException, IOException {
    try {
      UserFormDto userFormDto =
          new UserFormDto(req.getParameter("username"), req.getParameter("password"));
      UserDto userDto = userService.loginUser(userFormDto);
      req.getSession().setAttribute("user", userDto);
      res.sendRedirect(req.getContextPath() + "/");
    } catch (Exception e) {
      e.printStackTrace();
      req.setAttribute("error", e.getMessage());
      processRequest(req, res);
    }
  }
}
