package com.yohanmarcus.webshop.controller.admin;

import com.yohanmarcus.webshop.user.dto.UserDto;
import com.yohanmarcus.webshop.user.service.UserService;
import lombok.NoArgsConstructor;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import static com.yohanmarcus.webshop.util.JspDispatcher.WEB_INF_JSP_ADMIN_USERS_JSP;
import static com.yohanmarcus.webshop.util.JspDispatcher.processRequest;

@NoArgsConstructor
@WebServlet(name = "adminUserServlet", value = "/admin/users")
public class AdminUserController extends HttpServlet {

  @Inject private UserService userService;

  public AdminUserController(UserService userService) {
    this.userService = userService;
  }

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse res)
      throws ServletException, IOException {
    try {
      List<UserDto> userList = userService.findAll();
      req.setAttribute("users", userList);
      processRequest(req, res, WEB_INF_JSP_ADMIN_USERS_JSP);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }
}
