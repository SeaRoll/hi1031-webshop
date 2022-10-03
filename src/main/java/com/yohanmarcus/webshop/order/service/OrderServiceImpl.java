package com.yohanmarcus.webshop.order.service;

import com.yohanmarcus.webshop.item.dao.ItemDao;
import com.yohanmarcus.webshop.item.domain.Cart;
import com.yohanmarcus.webshop.item.domain.Item;
import com.yohanmarcus.webshop.order.dao.OrderDao;
import com.yohanmarcus.webshop.order.dao.OrderItemsDao;
import com.yohanmarcus.webshop.order.domain.Order;
import com.yohanmarcus.webshop.order.domain.OrderItems;
import com.yohanmarcus.webshop.order.domain.OrderStatus;
import com.yohanmarcus.webshop.order.domain.OrderWithItems;
import com.yohanmarcus.webshop.user.domain.User;
import com.yohanmarcus.webshop.util.TransactionFactory;
import com.yohanmarcus.webshop.util.TransactionManager;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class OrderServiceImpl implements OrderService {

  private final OrderDao orderDao;
  private final OrderItemsDao orderItemsDao;
  private final ItemDao itemDao;
  private final TransactionFactory transactionFactory;

  @Inject
  public OrderServiceImpl(
      OrderDao orderDao,
      OrderItemsDao orderItemsDao,
      ItemDao itemDao,
      TransactionFactory transactionFactory) {
    this.orderDao = orderDao;
    this.orderItemsDao = orderItemsDao;
    this.itemDao = itemDao;
    this.transactionFactory = transactionFactory;
  }

  @Override
  public void orderItems(Cart cart, User user) throws SQLException, IllegalStateException {
    TransactionManager tm = transactionFactory.begin();
    try {
      List<Item> items = itemDao.findAll(tm.getConn()); // todo: kan optimeras
      List<Item> cartItems = cart.getCartItems();

      if (cartItems.isEmpty()) throw new IllegalStateException("Cart is empty!");

      // create order first
      String orderId =
          orderDao.create(Order.of(null, user.getId(), OrderStatus.PLACED), tm.getConn());

      for (Item cartItem : cartItems) {
        // get item that is same as cart item
        Item itemWithSameId =
            items.stream()
                .filter(i -> i.getId().equals(cartItem.getId()))
                .findFirst()
                .orElseThrow(
                    () ->
                        new IllegalStateException(
                            String.format("Item with id %s does not exist!", cartItem.getId())));

        // check item quantity - cartItem quantity >= 0
        if (itemWithSameId.getQuantity() - cartItem.getQuantity() < 0) {
          throw new IllegalStateException(
              String.format("Too low supply on item %s", cartItem.getName()));
        }

        // create order-item entry
        var orderItem =
            OrderItems.of(
                null,
                orderId,
                cartItem.getName(),
                cartItem.getPrice(),
                cartItem.getQuantity(),
                cartItem.getDescription(),
                cartItem.getCategory());
        orderItemsDao.create(orderItem, tm.getConn());

        // update quantity
        itemWithSameId.setQuantity(itemWithSameId.getQuantity() - cartItem.getQuantity());
        itemDao.update(itemWithSameId, tm.getConn());
      }
      tm.commit();
    } finally {
      tm.close();
    }
  }

  @Override
  public List<OrderWithItems> getOrderByUser(User user) throws SQLException {
    TransactionManager tm = transactionFactory.begin();
    try {
      List<OrderWithItems> orderWithItems = new ArrayList<>();
      var orders = orderDao.findByUserId(user.getId(), tm.getConn());
      for (var order : orders) {
        orderWithItems.add(
            OrderWithItems.of(order, orderItemsDao.findByOrderId(order.getId(), tm.getConn())));
      }
      tm.commit();
      return orderWithItems;
    } finally {
      tm.close();
    }
  }
}
