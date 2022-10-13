package com.yohanmarcus.webshop.order;

import com.yohanmarcus.webshop.item.dao.ItemDao;
import com.yohanmarcus.webshop.item.dao.ItemDaoImpl;
import com.yohanmarcus.webshop.order.dao.OrderDao;
import com.yohanmarcus.webshop.order.dao.OrderDaoImpl;
import com.yohanmarcus.webshop.order.dao.OrderItemsDao;
import com.yohanmarcus.webshop.order.dao.OrderItemsDaoImpl;
import com.yohanmarcus.webshop.order.domain.Order;
import com.yohanmarcus.webshop.order.domain.OrderItems;
import com.yohanmarcus.webshop.order.domain.OrderStatus;
import com.yohanmarcus.webshop.user.dao.UserDao;
import com.yohanmarcus.webshop.user.dao.UserDaoImpl;
import com.yohanmarcus.webshop.user.domain.User;
import com.yohanmarcus.webshop.user.domain.UserRole;
import com.yohanmarcus.webshop.util.IntegrationTest;
import com.yohanmarcus.webshop.util.TestDomain.TestItem;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class OrderItemsDaoIntegrationTest extends IntegrationTest {

  private final OrderDao orderDao = new OrderDaoImpl();
  private final UserDao userDao = new UserDaoImpl();
  private final ItemDao itemDao = new ItemDaoImpl();
  private final OrderItemsDao orderItemsDao = new OrderItemsDaoImpl();
  private String orderId;
  private String orderId2;

  @BeforeEach
  void beforeEach() throws SQLException {
    orderItemsDao.removeAll(null);
    orderDao.removeAll(null);
    userDao.removeAll(null);
    itemDao.removeAll(null);

    itemDao.create(TestItem.of(null, "t", 3, 2, "", ""), null);
    String userId = userDao.create(User.of(null, "superadmin", "superadmin", UserRole.ADMIN), null);
    orderId = orderDao.create(Order.of(null, userId, OrderStatus.PLACED), null);
    orderId2 = orderDao.create(Order.of(null, userId, OrderStatus.PLACED), null);
  }

  @Test
  void testItemDaoFindAll_works() throws SQLException {
    var orderItem = OrderItems.of(null, orderId, "hello", 2, 3, "", "");
    orderItemsDao.create(orderItem, null);
    orderItemsDao.create(orderItem, null);
    orderItemsDao.create(orderItem, null);
    List<OrderItems> orderItems = orderItemsDao.findAll(null);
    assertEquals(3, orderItems.size());
  }

  @Test
  void testItemDaoFindByOrderId_works() throws SQLException {
    var orderItem = OrderItems.of(null, orderId, "hello", 2, 3, "", "");
    orderItemsDao.create(orderItem, null);
    orderItemsDao.create(orderItem, null);
    orderItem.setOrderId(orderId2);
    orderItemsDao.create(orderItem, null);
    List<OrderItems> orderItems = orderItemsDao.findByOrderId(orderId, null);
    assertEquals(2, orderItems.size());
  }

  @Test
  void testOrderItemsDaoCreate_savesNewItem() throws SQLException {
    var orderItem = OrderItems.of(null, orderId, "hello", 2, 3, "", "");
    String id = orderItemsDao.create(orderItem, null);
    OrderItems gotItem = orderItemsDao.findById(id, null).get();
    orderItem.setId(id);
    assertEquals(orderItem, gotItem);
  }

  @Test
  void testOrderItemsDaoUpdate() throws SQLException {
    var orderItem = OrderItems.of(null, orderId, "hello", 2, 3, "", "");
    String id = orderItemsDao.create(orderItem, null);
    orderItem.setId(id);
    orderItem.setName("bruv");
    orderItemsDao.update(orderItem, null);
    OrderItems gotItem = orderItemsDao.findById(id, null).get();
    assertEquals(orderItem, gotItem);
  }

  @Test
  void testOrderItemsDaoDelete() throws SQLException {
    var orderItem = OrderItems.of(null, orderId, "hello", 2, 3, "", "");
    String id = orderItemsDao.create(orderItem, null);
    orderItemsDao.removeById(id, null);
    assertTrue(orderItemsDao.findById(id, null).isEmpty());
  }
}
