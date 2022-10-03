package com.yohanmarcus.webshop.controller;

import com.yohanmarcus.webshop.user.domain.User;
import com.yohanmarcus.webshop.user.domain.UserRole;
import com.yohanmarcus.webshop.user.service.UserService;
import lombok.NoArgsConstructor;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import static com.yohanmarcus.webshop.util.JspDispatcher.WEB_INF_JSP_ADMIN_USERS_JSP;
import static com.yohanmarcus.webshop.util.JspDispatcher.processRequest;

@NoArgsConstructor
@WebServlet(name = "userServlet", value = "/admin/users")
public class UserController extends HttpServlet {

  @Inject private UserService userService;

  public UserController(UserService userService) {
    this.userService = userService;
  }

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse res)
      throws ServletException, IOException {
    try {
      List<User> userList = userService.findAll();
      req.setAttribute("users", userList);
      processRequest(req, res, WEB_INF_JSP_ADMIN_USERS_JSP);
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse res)
      throws ServletException, IOException {
    try {
      String id = req.getParameter("id");
      String username = req.getParameter("username");
      UserRole role = UserRole.valueOf(req.getParameter("role"));
      userService.updateUser(id, username, role);

      //      req.getSession().setAttribute("id", id);
      //      req.getSession().setAttribute("username", username);
      //      req.getSession().setAttribute("role", role);

      res.sendRedirect("/");
    } catch (Exception e) {
      e.printStackTrace();
      req.setAttribute("error", e.getMessage());
      processRequest(req, res, WEB_INF_JSP_ADMIN_USERS_JSP);
    }
  }
}
