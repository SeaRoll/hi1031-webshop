package com.yohanmarcus.webshop.item.service;

import com.yohanmarcus.webshop.item.domain.Cart;

import java.sql.SQLException;

public interface CartService {
  /**
   * Add item by item id to cart
   *
   * @param clickedId clicked item id
   * @param cart cart object to update
   * @return updated cart object
   * @throws SQLException sql error
   */
  Cart addToCart(String clickedId, Cart cart) throws SQLException;

  /**
   * Removes an item by item id on cart
   *
   * @param clickedId clicked item id
   * @param cart cart object to update
   * @return updated cart object
   * @throws SQLException sql error
   */
  Cart removeFromCart(String clickedId, Cart cart) throws SQLException;
}
