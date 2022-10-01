package com.yohanmarcus.webshop.controller;

import com.yohanmarcus.webshop.user.dao.UserDaoImpl;
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

@WebServlet(name = "registerServlet", value = "/register")
public class RegisterController extends HttpServlet {

  private UserService userService;

  public void init() {
    userService = new UserServiceImpl(new UserDaoImpl(), new TransactionManagerImpl());
  }

  private void processRequest(HttpServletRequest req, HttpServletResponse res)
      throws ServletException, IOException {
    RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/jsp/register.jsp");
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
      userService.registerUser(userForm);
      res.sendRedirect(req.getContextPath() + "/login");
    } catch (Exception e) {
      e.printStackTrace();
      req.setAttribute("error", e.getMessage());
      processRequest(req, res);
    }
  }
}
