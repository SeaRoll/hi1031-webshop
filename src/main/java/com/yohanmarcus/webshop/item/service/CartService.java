package com.yohanmarcus.webshop.item.service;

import com.yohanmarcus.webshop.item.domain.Cart;

import java.sql.SQLException;

public interface CartService {
  Cart addToCart(Integer clickedId, Cart cart) throws SQLException;

  Cart removeFromCart(Integer clickedId, Cart cart) throws SQLException;
}
