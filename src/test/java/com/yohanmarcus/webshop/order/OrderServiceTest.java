package com.yohanmarcus.webshop.order;

import com.yohanmarcus.webshop.item.dao.ItemDao;
import com.yohanmarcus.webshop.item.service.Cart;
import com.yohanmarcus.webshop.item.service.CartMapper;
import com.yohanmarcus.webshop.item.service.Item;
import com.yohanmarcus.webshop.order.dao.OrderDao;
import com.yohanmarcus.webshop.order.dao.OrderItemsDao;
import com.yohanmarcus.webshop.order.domain.Order;
import com.yohanmarcus.webshop.order.domain.OrderItems;
import com.yohanmarcus.webshop.order.domain.OrderStatus;
import com.yohanmarcus.webshop.order.dto.OrderDto;
import com.yohanmarcus.webshop.order.dto.OrderWithItems;
import com.yohanmarcus.webshop.order.service.OrderItemsMapper;
import com.yohanmarcus.webshop.order.service.OrderMapper;
import com.yohanmarcus.webshop.order.service.OrderService;
import com.yohanmarcus.webshop.order.service.OrderServiceImpl;
import com.yohanmarcus.webshop.user.domain.User;
import com.yohanmarcus.webshop.user.domain.UserRole;
import com.yohanmarcus.webshop.user.dto.UserDto;
import com.yohanmarcus.webshop.util.TestDomain.TestCart;
import com.yohanmarcus.webshop.util.TestDomain.TestItem;
import com.yohanmarcus.webshop.util.TransactionFactory;
import com.yohanmarcus.webshop.util.TransactionManager;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class OrderServiceTest {

  private final OrderDao orderDao = mock(OrderDao.class);
  private final OrderItemsDao orderItemsDao = mock(OrderItemsDao.class);
  private final ItemDao itemDao = mock(ItemDao.class);
  private final TransactionFactory tf = mock(TransactionFactory.class);
  private final TransactionManager tm = mock(TransactionManager.class);
  private final OrderService orderService =
      new OrderServiceImpl(orderDao, orderItemsDao, itemDao, tf);

  @Test
  void testOrderItems_commitsOnWorking() throws SQLException {
    List<Item> allItems =
        List.of(TestItem.of("1", "test", 2, 5, "", ""), TestItem.of("2", "hello", 3, 2, "", ""));
    Cart cart = new TestCart();
    Item item = allItems.get(0);
    cart.addToCart(item);
    var dto = CartMapper.toDto(cart);

    when(tf.begin()).thenReturn(tm);
    when(itemDao.findAll(any())).thenReturn(allItems);

    orderService.orderItems(dto, UserDto.from("1", "a", UserRole.ADMIN));

    verify(orderItemsDao)
        .create(
            OrderItems.of(
                any(),
                "1",
                item.getName(),
                item.getPrice(),
                4,
                item.getDescription(),
                item.getCategory()),
            any());
    verify(itemDao).update(eq(TestItem.of("1", "test", 2, 4, "", "")), any());
    verify(tm).commit();
    verify(tm).close();
  }

  @Test
  void testOrderItems_throwOnLowSupply() throws SQLException {
    List<Item> allItems =
        List.of(TestItem.of("1", "test", 2, 1, "", ""), TestItem.of("2", "hello", 3, 2, "", ""));
    Cart cart = new TestCart();
    cart.addToCart(allItems.get(0));
    cart.addToCart(allItems.get(0));

    var dto = CartMapper.toDto(cart);

    when(tf.begin()).thenReturn(tm);
    when(itemDao.findAll(any())).thenReturn(allItems);

    assertThrows(
        IllegalStateException.class,
        () -> orderService.orderItems(dto, UserDto.from("1", "a", UserRole.ADMIN)));

    verify(tm, times(0)).commit();
    verify(tm).close();
  }

  @Test
  void testGetOrderByUser() throws SQLException {
    UserDto user = UserDto.from("2", "admin", UserRole.USER);
    List<Order> order = List.of(Order.of("2", user.getId(), OrderStatus.PACKAGING));
    List<OrderItems> orderItems = List.of(OrderItems.of("1", "2", "a", 2, 3, "", ""));

    when(tf.begin()).thenReturn(tm);

    when(orderDao.findByUserId(eq(user.getId()), eq(null))).thenReturn(order);
    when(orderItemsDao.findByOrderId(eq("2"), eq(null))).thenReturn(orderItems);

    List<OrderWithItems> orderWithItems = orderService.getOrderByUser(user);
    var ordWItems = orderWithItems.get(0);

    assertEquals(orderItems.stream().map(OrderItemsMapper::toDto).toList(), ordWItems.getItems());
    assertEquals(OrderMapper.toDto(order.get(0)), ordWItems.getOrder());

    verify(tm).commit();
    verify(tm).close();
  }

  @Test
  void testGetOrderAll() throws SQLException {
    User user = User.of("2", "admin", "admin", UserRole.USER);
    List<Order> order = List.of(Order.of("2", user.getId(), OrderStatus.PACKAGING));
    List<OrderItems> orderItems = List.of(OrderItems.of("1", "2", "a", 2, 3, "", ""));

    when(tf.begin()).thenReturn(tm);

    when(orderDao.findAll(null)).thenReturn(order);
    when(orderItemsDao.findByOrderId(eq("2"), eq(null))).thenReturn(orderItems);

    List<OrderWithItems> orderWithItems = orderService.getAllOrders();
    var ordWItems = orderWithItems.get(0);

    assertEquals(orderItems.stream().map(OrderItemsMapper::toDto).toList(), ordWItems.getItems());
    assertEquals(OrderMapper.toDto(order.get(0)), ordWItems.getOrder());

    verify(tm).commit();
    verify(tm).close();
  }

  @Test
  void testGetOrderById_getsOrder() throws SQLException {
    Order order = Order.of("1", "123", OrderStatus.PLACED);
    when(orderDao.findById(eq("1"), eq(null))).thenReturn(Optional.ofNullable(order));
    OrderDto gotOrder = orderService.getOrderById("1");
    assert order != null;
    assertEquals(OrderMapper.toDto(order), gotOrder);
  }

  @Test
  void testGetOrderById_throwOnNotFound() throws SQLException {
    when(orderDao.findById(eq("1"), eq(null))).thenReturn(Optional.empty());
    assertThrows(IllegalStateException.class, () -> orderService.getOrderById("1"));
  }

  @Test
  void testUpdateOrderStatus_updatesOrder() throws SQLException {
    Order order = Order.of("1", "123", OrderStatus.PLACED);

    when(tf.begin()).thenReturn(tm);
    when(orderDao.findById(eq("1"), any())).thenReturn(Optional.ofNullable(order));

    assert order != null;
    orderService.updateOrderStatus(order.getId(), OrderStatus.PACKAGING);

    verify(orderDao).update(any(), any());
  }
}
