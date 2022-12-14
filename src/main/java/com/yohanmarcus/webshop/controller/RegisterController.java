package com.yohanmarcus.webshop.controller;

import com.yohanmarcus.webshop.user.form.UserForm;
import com.yohanmarcus.webshop.user.service.UserService;
import com.yohanmarcus.webshop.util.JspDispatcher;
import lombok.NoArgsConstructor;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.yohanmarcus.webshop.util.JspDispatcher.WEB_INF_JSP_REGISTER_JSP;

@NoArgsConstructor
@WebServlet(name = "registerServlet", value = "/register")
public class RegisterController extends HttpServlet {
  @Inject private UserService userService;

  public RegisterController(UserService userService) {
    this.userService = userService;
  }

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse res)
      throws ServletException, IOException {
    if (req.getSession().getAttribute("user") != null) {
      res.sendRedirect("/");
      return;
    }
    JspDispatcher.processRequest(req, res, WEB_INF_JSP_REGISTER_JSP);
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse res)
      throws ServletException, IOException {
    try {
      UserForm userForm = new UserForm(req.getParameter("username"), req.getParameter("password"));
      userService.registerUser(userForm);
      res.sendRedirect("/login");
    } catch (Exception e) {
      e.printStackTrace();
      req.setAttribute("error", e.getMessage());
      JspDispatcher.processRequest(req, res, WEB_INF_JSP_REGISTER_JSP);
    }
  }
}
