package com.yohanmarcus.webshop.order.service;

import com.yohanmarcus.webshop.item.domain.Cart;
import com.yohanmarcus.webshop.order.domain.OrderWithItems;
import com.yohanmarcus.webshop.user.domain.User;

import java.sql.SQLException;
import java.util.List;

public interface OrderService {
  void orderItems(Cart cart, User user) throws SQLException, IllegalStateException;

  List<OrderWithItems> getOrderByUser(User user) throws SQLException;
}
