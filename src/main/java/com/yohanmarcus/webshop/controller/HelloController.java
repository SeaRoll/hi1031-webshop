package com.yohanmarcus.webshop.controller;

import com.yohanmarcus.webshop.hello.HelloService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/** Våran controller för att skicka jsp till användare. */
@WebServlet(name = "helloServlet", value = "/")
public class HelloController extends HttpServlet {

  private HelloService helloService;
  private static final String HELLO_JSP = "/WEB-INF/jsp/hello-world.jsp";

  public void init() {
    helloService = new HelloService();
  }

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response)
      throws IOException, ServletException {

    // vi kan även sätta attributer här och skicka vidare till JSP
    request.setAttribute("helloAttribute", helloService.getHellos().get(0).getId());
    RequestDispatcher dispatcher = request.getRequestDispatcher(HELLO_JSP);
    dispatcher.forward(request, response);
  }
}
