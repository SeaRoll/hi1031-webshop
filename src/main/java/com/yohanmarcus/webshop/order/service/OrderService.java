package com.yohanmarcus.webshop.order.service;

import com.yohanmarcus.webshop.item.dto.CartDto;
import com.yohanmarcus.webshop.order.domain.OrderStatus;
import com.yohanmarcus.webshop.order.dto.OrderDto;
import com.yohanmarcus.webshop.order.dto.OrderWithItems;
import com.yohanmarcus.webshop.user.dto.UserDto;

import java.sql.SQLException;
import java.util.List;

public interface OrderService {

  OrderDto getOrderById(String id) throws SQLException;

  void updateOrderStatus(String id, OrderStatus orderStatus) throws SQLException;

  void orderItems(CartDto cart, UserDto user) throws SQLException, IllegalStateException;

  List<OrderWithItems> getOrderByUser(UserDto user) throws SQLException;

  List<OrderWithItems> getAllOrders() throws SQLException;
}
