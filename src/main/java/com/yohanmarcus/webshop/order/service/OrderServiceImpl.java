package com.yohanmarcus.webshop.order.service;

import com.yohanmarcus.webshop.item.dao.ItemDao;
import com.yohanmarcus.webshop.item.domain.Cart;
import com.yohanmarcus.webshop.item.domain.Item;
import com.yohanmarcus.webshop.order.dao.OrderDao;
import com.yohanmarcus.webshop.order.dao.OrderItemsDao;
import com.yohanmarcus.webshop.order.domain.Order;
import com.yohanmarcus.webshop.order.domain.OrderItems;
import com.yohanmarcus.webshop.order.domain.OrderItemsId;
import com.yohanmarcus.webshop.order.domain.OrderStatus;
import com.yohanmarcus.webshop.user.domain.User;
import com.yohanmarcus.webshop.util.TransactionFactory;
import com.yohanmarcus.webshop.util.TransactionManager;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.sql.SQLException;
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

      // create order first
      String orderId = orderDao.create(Order.of(null, user.getId(), OrderStatus.PLACED));

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
        OrderItemsId id = OrderItemsId.of(orderId, cartItem.getId());
        orderItemsDao.create(OrderItems.of(id, cartItem.getQuantity()), tm.getConn());

        // update quantity
        itemWithSameId.setQuantity(itemWithSameId.getQuantity() - cartItem.getQuantity());
        itemDao.update(itemWithSameId, tm.getConn());
      }
      tm.commit();
    } finally {
      tm.close();
    }
  }
}
