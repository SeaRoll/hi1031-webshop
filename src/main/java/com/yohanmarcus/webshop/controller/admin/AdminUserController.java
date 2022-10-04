package com.yohanmarcus.webshop.controller.admin;

import com.yohanmarcus.webshop.user.domain.User;
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
public class AdminUserController extends HttpServlet {

  @Inject private UserService userService;

  public AdminUserController(UserService userService) {
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
}
