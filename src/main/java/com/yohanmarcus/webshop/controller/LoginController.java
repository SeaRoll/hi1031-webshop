package com.yohanmarcus.webshop.controller;

import com.yohanmarcus.webshop.user.dao.UserDaoImpl;
import com.yohanmarcus.webshop.user.domain.User;
import com.yohanmarcus.webshop.user.dto.UserForm;
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

  private final UserService userService;

  public LoginController() {
    userService = new UserServiceImpl(new UserDaoImpl(), new TransactionManagerImpl());
  }

  public LoginController(UserService userService) {
    this.userService = userService;
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
      UserForm userForm = new UserForm(req.getParameter("username"), req.getParameter("password"));
      User user = userService.loginUser(userForm);
      req.getSession().setAttribute("user", user);
      res.sendRedirect("/");
    } catch (Exception e) {
      e.printStackTrace();
      req.setAttribute("error", e.getMessage());
      processRequest(req, res);
    }
  }
}
