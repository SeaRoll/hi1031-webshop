package com.yohanmarcus.webshop.controller.admin;

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

@NoArgsConstructor
@WebServlet(name = "deleteUserServlet", value = "/admin/users/delete")
public class AdminUserDeleteController extends HttpServlet {

  @Inject private UserService userService;

  public AdminUserDeleteController(UserService userService) {
    this.userService = userService;
  }

  @Override
  public void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    try {
      String id = req.getParameter("id");
      userService.removeById(id);
      resp.sendRedirect("/admin/users");
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }
}
