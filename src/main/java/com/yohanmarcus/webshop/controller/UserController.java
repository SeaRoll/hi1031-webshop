package com.yohanmarcus.webshop.controller;

import com.yohanmarcus.webshop.user.dao.UserDaoImpl;
import com.yohanmarcus.webshop.user.domain.User;
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
import java.sql.SQLException;
import java.util.List;

@WebServlet(name = "userServlet", value = "/admin/users")
public class UserController extends HttpServlet {

  private final UserService userService;

  public UserController() {
    userService = new UserServiceImpl(new UserDaoImpl(), new TransactionManagerImpl());
  }

  public UserController(UserService userService) {
    this.userService = userService;
  }

  private void processRequest(HttpServletRequest req, HttpServletResponse res)
      throws ServletException, IOException {
    RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/jsp/admin/users.jsp");
    dispatcher.forward(req, res);
  }

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse res)
      throws ServletException, IOException {
    try {
      List<User> userList = userService.findAll();
      req.setAttribute("users", userList);
      processRequest(req, res);
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }
}
