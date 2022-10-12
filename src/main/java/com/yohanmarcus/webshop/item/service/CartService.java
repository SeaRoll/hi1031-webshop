package com.yohanmarcus.webshop.item.service;

import com.yohanmarcus.webshop.item.dto.CartDto;

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
  CartDto addToCart(String clickedId, CartDto cart) throws SQLException;

  /**
   * Removes an item by item id on cart
   *
   * @param clickedId clicked item id
   * @param cart cart object to update
   * @return updated cart object
   * @throws SQLException sql error
   */
  CartDto removeFromCart(String clickedId, CartDto cart) throws SQLException;
}
