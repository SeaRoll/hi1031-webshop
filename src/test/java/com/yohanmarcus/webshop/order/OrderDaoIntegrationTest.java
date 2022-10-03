package com.yohanmarcus.webshop.order;

import com.yohanmarcus.webshop.order.dao.OrderDao;
import com.yohanmarcus.webshop.order.dao.OrderDaoImpl;
import com.yohanmarcus.webshop.order.domain.Order;
import com.yohanmarcus.webshop.order.domain.OrderStatus;
import com.yohanmarcus.webshop.user.dao.UserDao;
import com.yohanmarcus.webshop.user.dao.UserDaoImpl;
import com.yohanmarcus.webshop.user.domain.User;
import com.yohanmarcus.webshop.user.domain.UserRole;
import com.yohanmarcus.webshop.util.IntegrationTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class OrderDaoIntegrationTest extends IntegrationTest {

  private final OrderDao orderDao = new OrderDaoImpl();
  private final UserDao userDao = new UserDaoImpl();
  private String userId;

  @BeforeEach
  void beforeEach() throws SQLException {
    orderDao.removeAll(null);
    userDao.removeAll(null);
    userId = userDao.create(User.of(null, "superadmin", "superadmin", UserRole.ADMIN), null);
  }

  @Test
  void testOrderDaoCreate_savesNewItem() throws SQLException {
    Order order = Order.of(null, userId, OrderStatus.PLACED);
    String id = orderDao.create(order, null);
    order.setId(id);
    Order gotOrder = orderDao.findById(id, null).get();
    assertEquals(order, gotOrder);
  }

  @Test
  void testOrderDaoUpdate() throws SQLException {
    Order order = Order.of(null, userId, OrderStatus.PLACED);
    String id = orderDao.create(order, null);
    order.setId(id);
    order.setStatus(OrderStatus.PACKAGING);
    orderDao.update(order, null);
    Order gotOrder = orderDao.findById(id, null).get();
    assertEquals(order, gotOrder);
  }

  @Test
  void testOrderDaoDelete() throws SQLException {
    Order order = Order.of(null, userId, OrderStatus.PLACED);
    String id = orderDao.create(order, null);
    orderDao.removeById(id, null);
    assertTrue(orderDao.findById(id, null).isEmpty());
  }
}
