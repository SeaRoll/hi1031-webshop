package com.yohanmarcus.webshop.order.dao;

import com.yohanmarcus.webshop.order.domain.OrderItems;
import com.yohanmarcus.webshop.util.Dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface OrderItemsDao extends Dao<OrderItems, String> {
  List<OrderItems> findByOrderId(String orderId, Connection optionalConn) throws SQLException;
}
