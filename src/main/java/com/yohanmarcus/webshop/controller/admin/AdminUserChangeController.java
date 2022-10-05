package com.yohanmarcus.webshop.controller.admin;

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

import static com.yohanmarcus.webshop.util.JspDispatcher.WEB_INF_JSP_ADMIN_USER_CHANGE_JSP;
import static com.yohanmarcus.webshop.util.JspDispatcher.processRequest;

@NoArgsConstructor
@WebServlet(name = "adminUserChangeServlet", value = "/admin/users/change")
public class AdminUserChangeController extends HttpServlet {

  @Inject private UserService userService;

  public AdminUserChangeController(UserService userService) {
    this.userService = userService;
  }

  @Override
  public void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    try {
      String id = req.getParameter("id");
      if (id != null) {
        User user = userService.findById(id);
        req.setAttribute("user", user);
      } else
        throw new IllegalStateException(
            "Parameter id was empty, so the user could not be fetched!");
      processRequest(req, resp, WEB_INF_JSP_ADMIN_USER_CHANGE_JSP);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public void doPost(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    try {
      userService.updateUser(
          req.getParameter("id"),
          req.getParameter("username"),
          UserRole.valueOf(req.getParameter("role")));

      resp.sendRedirect("/admin/users");
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }
}
