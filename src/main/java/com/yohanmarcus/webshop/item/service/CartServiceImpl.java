package com.yohanmarcus.webshop.item.service;

import com.yohanmarcus.webshop.item.dao.ItemDao;
import com.yohanmarcus.webshop.item.domain.Cart;
import com.yohanmarcus.webshop.item.domain.Item;

import java.sql.SQLException;

public class CartServiceImpl implements CartService {

  private final ItemDao itemDao;

  public CartServiceImpl(ItemDao itemDao) {
    this.itemDao = itemDao;
  }

  @Override
  public Cart addToCart(Integer clickedId, Cart cart) throws SQLException {
    Item newItem = itemDao.findById(clickedId).orElseThrow();
    cart.addToCart(newItem);
    return cart;
  }

  @Override
  public Cart removeFromCart(Integer clickedId, Cart cart) throws SQLException {
    Item itemToRemove = itemDao.findById(clickedId).orElseThrow();
    cart.removeFromCart(itemToRemove);
    return cart;
  }
}
