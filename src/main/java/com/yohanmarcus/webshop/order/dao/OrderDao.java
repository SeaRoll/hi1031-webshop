package com.yohanmarcus.webshop.order.dao;

import com.yohanmarcus.webshop.order.domain.Order;
import com.yohanmarcus.webshop.util.Dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface OrderDao extends Dao<Order, String> {
  List<Order> findByUserId(String userId, Connection optionalConn) throws SQLException;
}
