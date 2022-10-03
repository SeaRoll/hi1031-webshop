package com.yohanmarcus.webshop.item.service;

import com.yohanmarcus.webshop.item.dao.ItemDao;
import com.yohanmarcus.webshop.item.domain.Cart;
import com.yohanmarcus.webshop.item.domain.Item;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.sql.SQLException;

@ApplicationScoped
public class CartServiceImpl implements CartService {

  private final ItemDao itemDao;

  @Inject
  public CartServiceImpl(ItemDao itemDao) {
    this.itemDao = itemDao;
  }

  @Override
  public Cart addToCart(String clickedId, Cart cart) throws SQLException {
    Item newItem = itemDao.findById(clickedId).orElseThrow();
    cart.addToCart(newItem);
    return cart;
  }

  @Override
  public Cart removeFromCart(String clickedId, Cart cart) throws SQLException {
    Item itemToRemove = itemDao.findById(clickedId).orElseThrow();
    cart.removeFromCart(itemToRemove);
    return cart;
  }
}
