package com.yohanmarcus.webshop.order;

import com.yohanmarcus.webshop.item.dao.ItemDao;
import com.yohanmarcus.webshop.item.dao.ItemDaoImpl;
import com.yohanmarcus.webshop.item.domain.Item;
import com.yohanmarcus.webshop.order.dao.OrderDao;
import com.yohanmarcus.webshop.order.dao.OrderDaoImpl;
import com.yohanmarcus.webshop.order.dao.OrderItemsDao;
import com.yohanmarcus.webshop.order.dao.OrderItemsDaoImpl;
import com.yohanmarcus.webshop.order.domain.Order;
import com.yohanmarcus.webshop.order.domain.OrderItems;
import com.yohanmarcus.webshop.order.domain.OrderItemsId;
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

class OrderItemsDaoIntegrationTest extends IntegrationTest {

  private final OrderDao orderDao = new OrderDaoImpl();
  private final UserDao userDao = new UserDaoImpl();
  private final ItemDao itemDao = new ItemDaoImpl();
  private final OrderItemsDao orderItemsDao = new OrderItemsDaoImpl();
  private String orderId;
  private String itemId;

  @BeforeEach
  void beforeEach() throws SQLException {
    orderDao.removeAll();
    userDao.removeAll();
    itemDao.removeAll();

    itemId = itemDao.create(Item.of(null, "t", 3, 2, "", ""));
    String userId = userDao.create(User.of(null, "superadmin", "superadmin", UserRole.ADMIN));
    orderId = orderDao.create(Order.of(null, userId, OrderStatus.PLACED));
  }

  @Test
  void testOrderItemsDaoCreate_savesNewItem() throws SQLException {
    OrderItemsId id = OrderItemsId.of(orderId, itemId);
    OrderItems orderItems = OrderItems.of(id, 2);
    orderItemsDao.create(orderItems);
    OrderItems gotItem = orderItemsDao.findById(id).get();
    assertEquals(orderItems, gotItem);
  }

  @Test
  void testOrderItemsDaoUpdate() throws SQLException {
    OrderItemsId id = OrderItemsId.of(orderId, itemId);
    OrderItems orderItems = OrderItems.of(id, 2);
    orderItemsDao.create(orderItems);
    orderItems.setQuantity(3);
    orderItemsDao.update(orderItems);

    OrderItems gotItem = orderItemsDao.findById(id).get();
    assertEquals(orderItems, gotItem);
  }

  @Test
  void testOrderItemsDaoDelete() throws SQLException {
    OrderItemsId id = OrderItemsId.of(orderId, itemId);
    OrderItems orderItems = OrderItems.of(id, 2);
    orderItemsDao.create(orderItems);
    orderItemsDao.removeById(id);
    assertTrue(orderItemsDao.findById(id).isEmpty());
  }
}