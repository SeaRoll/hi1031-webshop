package com.yohanmarcus.webshop.order.dao;

import com.yohanmarcus.webshop.order.domain.Order;
import com.yohanmarcus.webshop.util.Dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface OrderDao extends Dao<Order, String> {

  /**
   * Find list of orders by user id.
   *
   * @param userId user id to filter by
   * @param optionalConn connection if within a transaction
   * @return list of orders connected to a user
   * @throws SQLException sql error
   */
  List<Order> findByUserId(String userId, Connection optionalConn) throws SQLException;
}
