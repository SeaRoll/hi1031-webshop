package com.yohanmarcus.webshop.util;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/** Dispatches JSP to user */
public class JspDispatcher {

  public static final String WEB_INF_JSP_CART_JSP = "/WEB-INF/jsp/cart.jsp";
  public static final String WEB_INF_JSP_LOGIN_JSP = "/WEB-INF/jsp/login.jsp";
  public static final String WEB_INF_JSP_ORDER_JSP = "/WEB-INF/jsp/order.jsp";
  public static final String WEB_INF_JSP_PRODUCTS_JSP = "/WEB-INF/jsp/products.jsp";
  public static final String WEB_INF_JSP_REGISTER_JSP = "/WEB-INF/jsp/register.jsp";
  public static final String WEB_INF_JSP_ADMIN_ORDER_JSP = "/WEB-INF/jsp/admin-order.jsp";
  public static final String WEB_INF_JSP_ADMIN_USERS_JSP = "/WEB-INF/jsp/admin-users.jsp";
  public static final String WEB_INF_JSP_ADMIN_USER_CHANGE_JSP =
      "/WEB-INF/jsp/admin-user-change.jsp";
  public static final String WEB_INF_JSP_ADMIN_ORDER_CHANGE_JSP =
      "/WEB-INF/jsp/admin-order-change.jsp";
  public static final String WEB_INF_JSP_ADMIN_ITEM_JSP = "/WEB-INF/jsp/admin-item.jsp";
  public static final String WEB_INF_JSP_ADMIN_ITEM_CHANGE_JSP =
      "/WEB-INF/jsp/admin-item-change.jsp";

  /**
   * Sends a JSP Servlet to user
   *
   * @param req request
   * @param res response
   * @param file location of the file (starts from webapp folder)
   * @throws ServletException servlet error
   * @throws IOException io exception error
   */
  public static void processRequest(HttpServletRequest req, HttpServletResponse res, String file)
      throws ServletException, IOException {
    RequestDispatcher dispatcher = req.getRequestDispatcher(file);
    dispatcher.forward(req, res);
  }
}
