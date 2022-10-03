package com.yohanmarcus.webshop.order;

import com.yohanmarcus.webshop.item.dao.ItemDao;
import com.yohanmarcus.webshop.item.domain.Cart;
import com.yohanmarcus.webshop.item.domain.Item;
import com.yohanmarcus.webshop.order.dao.OrderDao;
import com.yohanmarcus.webshop.order.dao.OrderItemsDao;
import com.yohanmarcus.webshop.order.domain.OrderItems;
import com.yohanmarcus.webshop.order.service.OrderService;
import com.yohanmarcus.webshop.order.service.OrderServiceImpl;
import com.yohanmarcus.webshop.user.domain.User;
import com.yohanmarcus.webshop.user.domain.UserRole;
import com.yohanmarcus.webshop.util.TransactionFactory;
import com.yohanmarcus.webshop.util.TransactionManager;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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
        List.of(Item.of("1", "test", 2, 5, "", ""), Item.of("2", "hello", 3, 2, "", ""));
    Cart cart = new Cart();
    Item item = allItems.get(0);
    cart.addToCart(item);

    when(tf.begin()).thenReturn(tm);
    when(itemDao.findAll(any())).thenReturn(allItems);

    orderService.orderItems(cart, User.of("1", "a", "a", UserRole.ADMIN));

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
    verify(itemDao).update(eq(Item.of("1", "test", 2, 4, "", "")), any());
    verify(tm).commit();
    verify(tm).close();
  }

  @Test
  void testOrderItems_throwOnLowSupply() throws SQLException {
    List<Item> allItems =
        List.of(Item.of("1", "test", 2, 1, "", ""), Item.of("2", "hello", 3, 2, "", ""));
    Cart cart = new Cart();
    cart.addToCart(allItems.get(0));
    cart.addToCart(allItems.get(0));

    when(tf.begin()).thenReturn(tm);
    when(itemDao.findAll(any())).thenReturn(allItems);

    assertThrows(
        IllegalStateException.class,
        () -> orderService.orderItems(cart, User.of("1", "a", "a", UserRole.ADMIN)));

    verify(tm, times(0)).commit();
    verify(tm).close();
  }
}
